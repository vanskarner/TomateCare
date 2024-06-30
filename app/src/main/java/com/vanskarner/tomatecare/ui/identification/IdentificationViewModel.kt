package com.vanskarner.tomatecare.ui.identification

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.analysis.AnalysisComponent
import com.vanskarner.diseases.DiseasesComponent
import com.vanskarner.tomatecare.ui.common.BoundingBoxModel
import com.vanskarner.tomatecare.ui.common.toModel
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class IdentificationViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val diseasesComponent: DiseasesComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _identification = MutableLiveData<IdentificationDetailModel>()
    private val _note = MutableLiveData<String>()
    private val _summary = MutableLiveData<SummaryModel>()
    private val _leafInfo = MutableLiveData<LeafInfoModel>()
    private val _error = MutableLiveData<String>()
    private val _updatedNote = MutableLiveData<Unit>()

    val identification: LiveData<IdentificationDetailModel> = _identification
    val note: LiveData<String> = _note
    val summary: LiveData<SummaryModel> = _summary
    val leafInfo: LiveData<LeafInfoModel> = _leafInfo
    val error: LiveData<String> = _error
    val updatedNote: LiveData<Unit> = _updatedNote

    private var currentId = -1
    private var currentSummary = SummaryModel.empty()

    fun showAnalysis(analysisId: Int) {
        viewModelScope.launch {
            analysisComponent.findAnalysisDetail(analysisId)
                .onSuccess {
                    val plantImage = BitmapFactory.decodeFile(it.imagePath)
                    val leafModelList = it.listLeafBoxCoordinates.toModel()
                        .zip(it.classificationData)
                        .map { boxAndLeaf ->
                            val leafImage = cropImageFromBoundingBox(plantImage, boxAndLeaf.first)
                            boxAndLeaf.second.toLeafModel(leafImage)
                        }
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val creationDate = formatter.format(it.date)
                    val identificationDetailModel =
                        it.toIdentificationDetailModel(creationDate, leafModelList)
                    val recommendations = diseasesComponent.findByKeyCodes(it.diseaseKeyCodes)
                        .getOrDefault(emptyList())
                        .map { disease -> RecommendationModel(disease.name, disease.control) }
                    currentSummary = it.toSummaryModel(recommendations)
                    currentId = identificationDetailModel.id
                    _identification.value = identificationDetailModel
                }
                .onFailure { showError(it) }
        }
    }

    fun saveNote(text: String) {
        viewModelScope.launch {
            analysisComponent.updateAnalysisNote(currentId, text)
                .onSuccess { _updatedNote.value = Unit }
                .onFailure { showError(it) }
        }
    }

    fun getNote() {
        viewModelScope.launch {
            analysisComponent.getAnalysisNote(currentId)
                .onSuccess { _note.value = it }
                .onFailure { showError(it) }
        }
    }

    fun getSummary() {
        _summary.value = currentSummary
    }

    fun getLeafInfo(leafModel: LeafModel) {
        viewModelScope.launch {
            val symptoms = diseasesComponent.findByKeyCode(leafModel.keyCode)
                .getOrNull()?.symptoms ?: ""
            _leafInfo.value = leafModel.toLeafInfoModel(symptoms)
        }
    }

    private fun showError(error: Throwable) {
        _error.value = errorFilter.filter(error)
    }

    private fun cropImageFromBoundingBox(imgBitmap: Bitmap, boundingBox: BoundingBoxModel): Bitmap {
        val imageWidth = imgBitmap.width
        val imageHeight = imgBitmap.height
        val x1 = boundingBox.x1 * imageWidth
        val y1 = boundingBox.y1 * imageHeight
        val x2 = boundingBox.x2 * imageWidth
        val y2 = boundingBox.y2 * imageHeight
        val croppedImageWidth = (x2 - x1).toInt()
        val croppedImageHeight = (y2 - y1).toInt()
        if (croppedImageWidth <= 0 || croppedImageHeight <= 0)
            throw IllegalArgumentException("The dimensions of the area to be trimmed cannot be <= 0")
        val rectTarget = Rect(0, 0, croppedImageWidth, croppedImageHeight)
        val croppedImage =
            createBitmap(croppedImageWidth, croppedImageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(croppedImage)
        val rect = Rect(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        canvas.drawBitmap(imgBitmap, rect, rectTarget, null)
        return croppedImage
    }

}
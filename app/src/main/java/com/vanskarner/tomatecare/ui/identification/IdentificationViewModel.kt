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
import com.vanskarner.analysis.LeafState
import com.vanskarner.tomatecare.ui.common.BoundingBoxModel
import com.vanskarner.tomatecare.ui.common.toModel
import com.vanskarner.tomatecare.ui.errors.ErrorFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class IdentificationViewModel @Inject constructor(
    private val analysisComponent: AnalysisComponent,
    private val errorFilter: ErrorFilter
) : ViewModel() {
    private val _identification = MutableLiveData<IdentificationDetailModel>()
    private val _note = MutableLiveData<String>()
    private val _summary = MutableLiveData<SummaryModel>()
    private val _leafInfo = MutableLiveData<LeafInfoModel>()
    private val _error = MutableLiveData<String>()
    private val _updatedNote = MutableLiveData<Unit>()
    private val _boundingBoxes = MutableLiveData<List<BoundingBoxModel>>()

    val identification: LiveData<IdentificationDetailModel> = _identification
    val note: LiveData<String> = _note
    val summary: LiveData<SummaryModel> = _summary
    val leafInfo: LiveData<LeafInfoModel> = _leafInfo
    val error: LiveData<String> = _error
    val updatedNote: LiveData<Unit> = _updatedNote
    val boundingBoxes: LiveData<List<BoundingBoxModel>> = _boundingBoxes

    private fun showError(msg: String) {
        _error.value = msg
    }

    fun showAnalysis(analysisId: Int) {
        viewModelScope.launch {
            analysisComponent.findAnalysisDetail(analysisId)
                .onSuccess {
                    val plantImage = BitmapFactory.decodeFile(it.imagePath)
                    val leafModelList = it.listLeafBoxCoordinates.toModel()
                        .zip(it.classificationData)
                        .map { boxAndLeaf ->
                            val leafImage = cropImageFromBoundingBox(plantImage, boxAndLeaf.first)
                            val diseaseName = boxAndLeaf.second.bestPrediction.first
                            val isHealthy = boxAndLeaf.second.leafState == LeafState.Healthy
                            val probability = boxAndLeaf.second.bestPrediction.second
                            LeafModel(
                                image = leafImage,
                                diseases = diseaseName,
                                isHealthy = isHealthy,
                                probability = probability
                            )
                        }
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val creationDate = formatter.format(it.date)
                    val identificationDetailModel = IdentificationDetailModel(
                        id = it.id,
                        creationDate = creationDate,
                        plantImage = plantImage,
                        leavesImage = leafModelList,
                        description = it.note
                    )
                    _boundingBoxes.value = it.listLeafBoxCoordinates.toModel()
                    _identification.value = identificationDetailModel
                }
                .onFailure { showError(errorFilter.filter(it)) }
        }
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

    fun saveNote(analysisId: Int, text: String) {
        viewModelScope.launch {
            analysisComponent.updateAnalysisNote(analysisId, text)
                .onSuccess { _updatedNote.value = Unit }
                .onFailure { showError(errorFilter.filter(it)) }
        }
    }

    fun getNote(analysisId: Int) {
        viewModelScope.launch {
            analysisComponent.getAnalysisNote(analysisId)
                .onSuccess { _note.value = it }
                .onFailure { showError(errorFilter.filter(it)) }
        }
    }

    fun getSummary() {
        val recomendations = listOf(
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
            RecommendationModel("Mosaic Virus", " Alguna descripcion"),
            RecommendationModel("Baterial Spot", "Otra descripcion"),
        )
//        val recomendations = emptyList<RecommendationModel>()
        val summaryModel =
            SummaryModel("30 ms", 20, 2, "Mosaic Virus \nBaterial Spot", recomendations)
        _summary.value = summaryModel
    }

    fun getLeafInfo() {
//        _leafInfo.value = model
    }

}
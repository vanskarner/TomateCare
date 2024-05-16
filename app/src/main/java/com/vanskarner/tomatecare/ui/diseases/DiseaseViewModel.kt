package com.vanskarner.tomatecare.ui.diseases

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanskarner.diseases.DiseasesComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DiseaseViewModel @Inject constructor(
    private val diseasesComponent: DiseasesComponent
) : ViewModel() {
    private val _diseases = MutableLiveData<List<DiseaseModel>>()
    private val _diseaseDetail = MutableLiveData<DiseaseDetailModel>()


    val diseases: LiveData<List<DiseaseModel>> = _diseases
    val diseaseDetail: LiveData<DiseaseDetailModel> = _diseaseDetail


    fun getDiseases() {
        viewModelScope.launch {
            diseasesComponent.getList()
                .onSuccess {
                    println("Mensaje onSuccess"+it.size)
                    _diseases.value = it.toListModel()
                }
                .onFailure {
                    println("Mensaje onFailure"+it.message)
                }
        }
    }

    //just to see how it looks, then delete
    fun exampleDiseaseDetail() {
        val bitmap = exampleBitmap()
        val item = DiseaseDetailModel(
            1,
            bitmap,
            "Bacterial Spot",
            "1Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.",
            "2Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.",
            "3Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.",
            "4Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen.",
        )
        _diseaseDetail.value = item
    }

    //just to see how it looks, then delete
    private fun exampleBitmap(): Bitmap {
        // Tamaño del bitmap
        val ancho = 200
        val alto = 200

        // Crear un bitmap con el tamaño especificado
        val bitmap = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888)

        // Crear un lienzo para dibujar en el bitmap
        val canvas = Canvas(bitmap)

        // Dibujar un rectángulo de fondo blanco
        val paint = Paint().apply {
            color = Color.WHITE
        }
        canvas.drawRect(0f, 0f, ancho.toFloat(), alto.toFloat(), paint)

        // Dibujar un círculo rojo en el centro
        paint.color = Color.RED
        val radio = 50f
        val centroX = ancho / 2f
        val centroY = alto / 2f
        canvas.drawCircle(centroX, centroY, radio, paint)

        // Dibujar un texto negro
        paint.color = Color.BLACK
        paint.textSize = 30f
        val texto = "My Image"
        val textoAncho = paint.measureText(texto)
        val textoX = centroX - textoAncho / 2
        canvas.drawText(texto, textoX, centroY, paint)

        return bitmap
    }

}
package com.vanskarner.tomatecare.identification

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IdentificationViewModel : ViewModel() {
    private val _identification = MutableLiveData<IdentificationDetailModel>()

    val identification: LiveData<IdentificationDetailModel> = _identification

    //just to see how it looks, then delete
    fun exampleData() {
        val bitmap = exampleBitmap()
        val leavesImage = listOf(
            LeafModel(bitmap, true, "", 95.5f),
            LeafModel(bitmap, false, "Bacterial Spot", 95.5f),
            LeafModel(bitmap, false, "Early Blight", 95.5f),
            LeafModel(bitmap, false, "Late Blight", 95.5f),
            LeafModel(bitmap, true, "", 95.5f),
            LeafModel(bitmap, false, "Leaf Mold", 95.5f),
            LeafModel(bitmap, false, "Mosaic Virus", 95.5f),
            LeafModel(bitmap, true, "", 95.5f),
            LeafModel(bitmap, false, "Septoria Leaf Spot", 95.5f),
            LeafModel(bitmap, false, "Target Spot", 95.5f),
            LeafModel(bitmap, true, "", 95.5f),
            LeafModel(bitmap, false, "Two Spotted Spider Mite", 95.5f),
            LeafModel(bitmap, false, "Yellow Leaf Curl Virus", 95.5f),
        )
        val data =
            IdentificationDetailModel(
                1,
                "3 Mayo 2024",
                bitmap,
                leavesImage,
                "Some description from the user about the context of the image"
            )
        _identification.value = data
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
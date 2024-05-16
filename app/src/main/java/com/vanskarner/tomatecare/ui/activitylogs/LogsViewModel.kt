package com.vanskarner.tomatecare.ui.activitylogs

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LogsViewModel @Inject constructor(): ViewModel() {
    private val _list = MutableLiveData<List<LogModel>>()

    val list: LiveData<List<LogModel>> = _list

    val bitmap = exampleBitmap()
    private var exampleLogs = listOf(
        LogModel(
            1, false, bitmap, "Enfermedad: 8", "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.", "24 abril 2024", false
        ),
        LogModel(
            1, true, bitmap, "Saludable", "alguna nota", "25 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad:1", "alguna nota", "26 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad:2", "alguna nota", "27 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad:3", "alguna nota", "28 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad:4", "alguna nota", "2 abril 2024", false
        ),
        LogModel(
            1, true, bitmap, "Saludable", "alguna nota", "1 abril 2024", false
        ),
        LogModel(
            1, true, bitmap, "Saludable", "alguna nota", "2 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad: 5", "alguna nota", "3 abril 2024", false
        ),
        LogModel(
            1, false, bitmap, "Enfermedad: 6", "alguna nota", "4 abril 2024", false
        ),
    )

    fun exampleList() {
        _list.value = exampleLogs
    }

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
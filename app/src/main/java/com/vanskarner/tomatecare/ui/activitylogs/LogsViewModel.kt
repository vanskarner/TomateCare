package com.vanskarner.tomatecare.ui.activitylogs

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LogsViewModel @Inject constructor() : ViewModel() {
    private val _list = MutableLiveData<List<LogModel>>()
    private val _msgDelete = MutableLiveData<Unit>()
    private val _msgNoItemSelected = MutableLiveData<Unit>()
    private val _restart = MutableLiveData<Unit>()
    private val bitmap = exampleBitmap()
    private var fullList = mutableListOf(
        LogModel(
            1,
            false,
            bitmap,
            "Enfermedad 1",
            "Nota 1",
            "24 abril 2024",
            false
        ),
        LogModel(
            2, true, bitmap, "Saludable 2", "Nota 2", "25 abril 2024", false
        ),
        LogModel(
            3, false, bitmap, "Enfermedad 3", "Nota 3", "26 abril 2024", false
        ),
        LogModel(
            4, false, bitmap, "Enfermedad 4", "Nota 4", "27 abril 2024", false
        ),
        LogModel(
            5, false, bitmap, "Enfermedad 5", "Nota 5", "28 abril 2024", false
        ),
        LogModel(
            6, false, bitmap, "Enfermedad 6", "Nota 6", "2 abril 2024", false
        ),
        LogModel(
            7, true, bitmap, "Saludable 7", "Nota 7", "1 abril 2024", false
        ),
        LogModel(
            8, true, bitmap, "Saludable 8", "Nota 8", "2 abril 2024", false
        ),
        LogModel(
            9, false, bitmap, "Enfermedad 9", "Nota 9", "3 abril 2024", false
        ),
        LogModel(
            10, false, bitmap, "Enfermedad 10", "Nota 10", "4 abril 2024", false
        ),
    )
    private var filterList = mutableListOf<LogModel>()

    val list: LiveData<List<LogModel>> = _list
    val msgDelete: LiveData<Unit> = _msgDelete
    val noItemSelected: LiveData<Unit> = _msgNoItemSelected
    val restart: LiveData<Unit> = _restart

    fun getData() {
        _list.value = fullList
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

    fun checkSelections() {
        val hasSelectedItems = fullList.any { it.checked }
        if (hasSelectedItems) _msgDelete.value = Unit
        else _msgNoItemSelected.value = Unit
    }

    fun deleteSelectedItems() {
        fullList.removeIf { it.checked }
        _list.value = fullList
        _restart.value = Unit
    }

    fun filterByNote(name: String) {
        viewModelScope.launch {
            filterList.clear()
            val query = name.lowercase().trim()
            for (item in fullList)
                if (item.note.lowercase().contains(query))
                    filterList.add(item)
            _list.value = filterList
        }
    }

}
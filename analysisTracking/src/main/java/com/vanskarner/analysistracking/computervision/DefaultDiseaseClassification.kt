package com.vanskarner.analysistracking.computervision

import android.content.Context
import android.util.Log
import com.vanskarner.analysistracking.LeafState
import com.vanskarner.analysistracking.Predictions
import com.vanskarner.analysistracking.bussineslogic.DiseaseClassification
import com.vanskarner.analysistracking.ml.TomatoDiseaseMobilenetv2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.math.exp
import java.nio.ByteBuffer

class DefaultDiseaseClassification(private val context: Context) : DiseaseClassification {
    private val tag = "DefaultDiseaseClassification"
    private val categories = listOf(
        "bacterial_spot",
        "early_blight",
        "healthy",
        "late_blight",
        "leaf_mold",
        "mosaic_virus",
        "septoria_leaf_spot",
        "target_spot",
        "twospotted_spider_mite",
        "yellow_leaf_curl_virus"
    )

    override suspend fun predict(byteBuffer: ByteBuffer): Result<Predictions> {
        val model = TomatoDiseaseMobilenetv2.newInstance(context)
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val allProbabilities = mutableListOf<Pair<String, Float>>()
        val allProbabilitiesDouble = softmax(outputFeature0.floatArray)
        allProbabilitiesDouble.forEachIndexed { index, d ->
            val miFloat= "%,.6f".format(d).toFloat()
            allProbabilities.add(Pair(categories[index],miFloat))
        }
        model.close()
        return Result.success(
            Predictions(
                LeafState.Healthy,
                "",
                0.95f,
                allProbabilities
            )
        )
    }

    private fun softmax(predictions: FloatArray): DoubleArray {
        val max = predictions.maxOrNull() ?: 0.0f
        val expValues = predictions.map {
            exp((it - max).toDouble())
        }
        val sumExpValues = expValues.sum()
        return expValues.map {
            (it / sumExpValues)
        }.toDoubleArray()
    }


}
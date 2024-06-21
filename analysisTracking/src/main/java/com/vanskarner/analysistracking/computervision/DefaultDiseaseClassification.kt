package com.vanskarner.analysistracking.computervision

import android.content.Context
import com.vanskarner.analysistracking.Classification
import com.vanskarner.analysistracking.bussineslogic.DiseaseClassification
import java.nio.ByteBuffer

class DefaultDiseaseClassification(private val context: Context) : DiseaseClassification {

    override suspend fun predict(byteBuffer: ByteBuffer): Result<Classification> {
        TODO()
    }

}
package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.Predictions
import java.nio.ByteBuffer

interface DiseaseClassification {

    suspend fun predict(byteBuffer: ByteBuffer):Result<Predictions>

}
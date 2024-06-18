package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.Classification
import java.nio.ByteBuffer

interface DiseaseClassification {

    suspend fun predict(byteBuffer: ByteBuffer):Result<Classification>

}
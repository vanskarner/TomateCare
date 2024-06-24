package com.vanskarner.analysistracking.bussineslogic

import com.vanskarner.analysistracking.BoundingBoxData
import com.vanskarner.analysistracking.ClassificationData
import java.nio.ByteBuffer

interface DiseaseClassification {

    suspend fun predict(byteBuffer: ByteBuffer):Result<ClassificationData>

    suspend fun analyze(imgPath: String):Pair<Long, List<BoundingBoxData>>

}
package com.vanskarner.analysis

import java.io.InputStream

interface AnalysisComponent {

    suspend fun getAnalysis(): Result<List<AnalysisData>>

    suspend fun analyzePlant(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData>

    suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData>

    fun getConfigParams(): ConfigData

    suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit>

    suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int>

    suspend fun getAnalysisNote(id: Int): Result<String>

    /**
     * Performs a performance test of both the leaf detection model and the tomato disease
     * classification model. The default leaf detection model for this test is YOLOV8n.
     * @param config test configuration parameters
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the inference times, the first value is for detection
     * and the second for classification.
     * - [Result.onFailure], one of the following errors occurred
     * [AnalysisError.InvalidConfig],[AnalysisError.InvalidModel],[AnalysisError.GPUNotSupportedByDevice]
     */
    suspend fun performanceTest(config: TestConfigData): Result<Pair<Long, Long>>

    /**
     * Get the images used for testing in InputStream format. The first value is used for
     * detection and the second for classification.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns Pair
     * - [Result.onFailure], not mapped
     */
    suspend fun getImagesUsedForTest():Result<Pair<InputStream,InputStream>>

}
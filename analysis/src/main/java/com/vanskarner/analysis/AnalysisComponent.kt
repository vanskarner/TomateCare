package com.vanskarner.analysis

interface AnalysisComponent {

    suspend fun getAnalysis(): Result<List<AnalysisData>>

    suspend fun analyzePlant(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData>

    suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData>

    fun getConfigParams(): ConfigData

    suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit>

    suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int>

    suspend fun getAnalysisNote(id: Int): Result<String>

/*    *//**
     * Performs a performance test of both the leaf detection model and the tomato disease
     * classification model. The default leaf detection model for this test is YOLOV8n.
     * @param config test configuration parameters
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns [TestInfoData]: It contains the inference times and
     * input streams of the images used for the test.
     * - [Result.onFailure], one of the following errors occurred
     *//*
    suspend fun performanceTest(config: TestConfigData): Result<TestInfoData>*/

}
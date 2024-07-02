package com.vanskarner.analysis

import java.io.InputStream

/**
 * Interface for components responsible for analyzing plant images,
 * detecting leaves, classifying their health status, and managing analysis data.
 * @author Luis H. Olazo
 * @version 1.0.0
 */
interface AnalysisComponent {

    /**
     * Gets all saved analyses.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns analysis
     * - [Result.onFailure], does not generate any type of error in [AnalysisError]
     */
    suspend fun getAnalysis(): Result<List<AnalysisData>>

    /**
     * Mainly performs the following in an orderly fashion:
     * 1. uses an object detection model to identify leaves and provide the coordinates of the
     * corresponding bounding boxes.
     * 2. Each identified leaf is subjected to a classifier model that determines its health
     * status, categorizing it as bacterial_spot, early_blight, healthy, late_blight, leaf_mold,
     * mosaic_virus, septoria_leaf_spot, target_spot, twospotted_spider_mite,
     * or yellow_leaf_curl_virus.
     * 3. Stores relevant information generated during analysis.
     * @param imgPath absolute path of the image of the plant to analyze
     * @param configData configuration for analysis
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], return completed analysis
     * - [Result.onFailure], one of the following errors occurred: [AnalysisError.InvalidConfig],
     * [AnalysisError.NoLeaves],[AnalysisError.GPUNotSupportedByDevice]
     */
    suspend fun analyzePlant(imgPath: String, configData: SetConfigData): Result<AnalysisDetailData>

    /**
     * Find the saved analysis by ID.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the analysis
     * - [Result.onFailure], one of the following errors occurred: [AnalysisError.NotFound]
     */
    suspend fun findAnalysisDetail(id: Int): Result<AnalysisDetailData>

    /**
     * Get the valid and allowed configuration parameters used by this component.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the allowed configuration
     * - [Result.onFailure], does not generate any type of error in [AnalysisError]
     */
    fun getConfigParams(): ConfigData

    /**
     * Updates the saved analysis note by its ID.
     * @param id identifier
     * @param note note to update
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], it only confirms the success of the operation
     * - [Result.onFailure], one of the following errors occurred: [AnalysisError.NotFound]
     */
    suspend fun updateAnalysisNote(id: Int, note: String): Result<Unit>

    /**
     * Deletes the saved tests by specifying the list of identifiers and returns the number of
     * deleted items. If an invalid id is supplied in the list, it will be omitted and will not
     * generate an error.
     * @param ids identifier list
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the number of elements removed
     * - [Result.onFailure], does not generate any type of error in [AnalysisError]
     */
    suspend fun deleteAnalysisByIds(ids: List<Int>): Result<Int>

    /**
     * Gets the saved analysis note by specifying its ID
     * @param id analysis identifier
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the nota del analisis
     * - [Result.onFailure], one of the following errors occurred: [AnalysisError.NotFound]
     */
    suspend fun getAnalysisNote(id: Int): Result<String>

    /**
     * Performs a performance test of both the leaf detection model and the tomato disease
     * classification model. The default leaf detection model for this test is YOLOV8n.
     * For the detection test an image is used and the resulting inference time is the total time
     * taken to detect all the leaves present in the image.
     * For the classification test, 10 images are used and the resulting inference time is the
     * total time spent on the 10 images.
     * @param config test configuration parameters
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns the inference times, the first value is for detection
     * and the second for classification.
     * - [Result.onFailure], one of the following errors occurred
     * [AnalysisError.InvalidConfig],[AnalysisError.InvalidModel],
     * [AnalysisError.GPUNotSupportedByDevice]
     */
    suspend fun performanceTest(config: TestConfigData): Result<Pair<Long, Long>>

    /**
     * Get the images used for testing in InputStream format. The first value is used for
     * detection and the second for classification. For the classification test, the 10 images
     * used for the test have been put together in a single image, where the images marked with a
     * red x represent the diseases and the green check represents the healthy leaf.
     * @return [Result], encapsulates success or failure.
     * - [Result.onSuccess], returns Pair
     * - [Result.onFailure], not mapped
     */
    suspend fun getImagesUsedForTest():Result<Pair<InputStream,InputStream>>

}
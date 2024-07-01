package com.vanskarner.analysis.computervision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.vanskarner.analysis.AnalysisError
import com.vanskarner.analysis.bussineslogic.ComputerVisionPerformance
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.InputStream

class TFLiteComputerVisionPerformance(private val context: Context) : ComputerVisionPerformance {

    override suspend fun imageUsedForDetection(): InputStream {
        return context.assets.open("leaf_detection_test.jpg")
    }

    override suspend fun imagesUsedForClassification(): InputStream {
        return context.assets.open("all_diseases_test.jpg")
    }

    override suspend fun testYoloV8nModel(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val imgBitmap = loadBitmapFromAssets("leaf_detection_test.jpg")
                    val result = useYoloV8LeafDetection(context, imgBitmap, options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val imgBitmap = loadBitmapFromAssets("leaf_detection_test.jpg")
            val result = useYoloV8LeafDetection(context, imgBitmap, options).getOrThrow()
            return Result.success(result.first)
        }
    }

    override suspend fun testMobileNetV3SmallModel(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val result = useMobilenetV3Small(context, imagesForClassification(), options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val result =
                useMobilenetV3Small(context, imagesForClassification(), options).getOrThrow()
            return Result.success(result.first)
        }
    }

    override suspend fun testMobileNetV3LargeModel(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val result = useMobilenetV3Large(context, imagesForClassification(), options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val result =
                useMobilenetV3Large(context, imagesForClassification(), options).getOrThrow()
            return Result.success(result.first)
        }
    }

    override suspend fun testMobileNetV2Model(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val result = useMobilenetV2(context, imagesForClassification(), options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val result = useMobilenetV2(context, imagesForClassification(), options).getOrThrow()
            return Result.success(result.first)
        }
    }

    override suspend fun testSqueezeNetMishModel(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val result = useSqueezeNetMish(context, imagesForClassification(), options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val result = useSqueezeNetMish(context, imagesForClassification(), options).getOrThrow()
            return Result.success(result.first)
        }
    }

    override suspend fun testNASNetMobileModel(
        useGPU: Boolean,
        threads: Int
    ): Result<Long> {
        return runCatching {
            if (useGPU) {
                val compatList = CompatibilityList()
                val isDelegateSupportedOnThisDevice = compatList.isDelegateSupportedOnThisDevice
                if (isDelegateSupportedOnThisDevice) {
                    val delegate = GpuDelegate(compatList.bestOptionsForThisDevice)
                    val options = Interpreter.Options().addDelegate(delegate)
                    val result = useNASNetMobile(context, imagesForClassification(), options)
                        .getOrThrow()
                    compatList.close()
                    delegate.close()
                    return Result.success(result.first)
                }
                return Result.failure(AnalysisError.GPUNotSupportedByDevice)
            }
            val options = Interpreter.Options().setNumThreads(threads)
            val result = useNASNetMobile(context, imagesForClassification(), options).getOrThrow()
            return Result.success(result.first)
        }
    }

    private fun imagesForClassification(): List<Bitmap> = listOf(
        loadBitmapFromAssets("img_bs_288.jpg"),
        loadBitmapFromAssets("img_eb_210.jpg"),
        loadBitmapFromAssets("img_h_138.jpg"),
        loadBitmapFromAssets("img_lb_163.jpg"),
        loadBitmapFromAssets("img_lm_175.jpg"),
        loadBitmapFromAssets("img_mv_308.jpg"),
        loadBitmapFromAssets("img_sls_31.jpg"),
        loadBitmapFromAssets("img_ts_128.jpg"),
        loadBitmapFromAssets("img_tsm_291.jpg"),
        loadBitmapFromAssets("img_ylcv_348.jpg")
    )

    private fun loadBitmapFromAssets(fileName: String): Bitmap {
        val inputStream = context.assets.open(fileName)
        val imgBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        return imgBitmap
    }

}
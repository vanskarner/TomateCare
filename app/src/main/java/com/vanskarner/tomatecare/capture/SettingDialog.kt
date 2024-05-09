package com.vanskarner.tomatecare.capture

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vanskarner.tomatecare.databinding.DialogCaptureSettingsBinding
import java.text.DecimalFormat

class SettingDialog : DialogFragment() {
    private val tag = "SettingDialog"
    private lateinit var settingModel: SettingModel
    private lateinit var onClick: (setting: SettingModel) -> Unit

    fun show(fragmentManager: FragmentManager, model: SettingModel) {
        this.settingModel = model
        super.show(fragmentManager, tag)
    }

    fun setOnApply(listener: (item: SettingModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bindingSetting = DialogCaptureSettingsBinding.inflate(layoutInflater)
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setView(bindingSetting.root)
        val adapterModels =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                settingModel.models
            )
        val adapterProcessors =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                settingModel.processors
            )
        adapterModels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterProcessors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bindingSetting.spModel.adapter = adapterModels
        bindingSetting.spDelegate.adapter = adapterProcessors
        bindingSetting.tvThreshold.text = "${settingModel.threshold}"
        bindingSetting.tvMaxResults.text = "${settingModel.maxResults}"
        bindingSetting.tvThreads.text = "${settingModel.threads}"
        bindingSetting.btnLessThreshold.setOnClickListener { decreaseThreshold(bindingSetting) }
        bindingSetting.btnMoreThreshold.setOnClickListener { incrementThreshold(bindingSetting) }
        bindingSetting.btnLessResults.setOnClickListener { decreaseResults(bindingSetting) }
        bindingSetting.btnMoreResults.setOnClickListener { incrementResults(bindingSetting) }
        bindingSetting.btnLessThreads.setOnClickListener { decreaseThreads(bindingSetting) }
        bindingSetting.btnMoreThreads.setOnClickListener { incrementThreads(bindingSetting) }
        bindingSetting.btnApply.setOnClickListener { onClick.invoke(settingModel) }
        return alertBuilder.create()
    }

    private fun incrementThreshold(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.threshold = increase(
            settingModel.threshold,
            settingModel.thresholdLimit,
            settingModel.thresholdIncrease
        )
        bindingSetting.tvThreshold.text = "${settingModel.threshold}"
    }

    private fun incrementResults(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.maxResults = increaseCounter(settingModel.maxResults, settingModel.resultsLimit)
        bindingSetting.tvMaxResults.text = "${settingModel.maxResults}"
    }

    private fun incrementThreads(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.threads = increaseCounter(settingModel.threads, settingModel.threadLimit)
        bindingSetting.tvThreads.text = "${settingModel.threads}"
    }

    private fun decreaseThreshold(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.threshold = decrease(
            settingModel.threshold,
            settingModel.thresholdIncrease
        )
        bindingSetting.tvThreshold.text = "${settingModel.threshold}"
    }

    private fun decreaseResults(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.maxResults =
            decreaseCounter(settingModel.maxResults)
        bindingSetting.tvMaxResults.text = "${settingModel.maxResults}"
    }

    private fun decreaseThreads(bindingSetting: DialogCaptureSettingsBinding) {
        settingModel.threads = decreaseCounter(settingModel.threads)
        bindingSetting.tvThreads.text = "${settingModel.threads}"
    }

    private fun increase(actualValue: Float, limitValue: Float, increment: Float): Float {
        val newValue = actualValue + increment
        val result = if (newValue <= limitValue) newValue else limitValue
        return roundToTwoDecimals(result)
    }

    private fun decrease(actualValue: Float, decrement: Float): Float {
        val newValue = actualValue - decrement
        val result = if (newValue >= decrement) newValue else actualValue
        return roundToTwoDecimals(result)
    }

    private fun increaseCounter(actualValue: Int, limitValue: Int): Int {
        val newValue = actualValue + 1
        return if (newValue <= limitValue) newValue
        else limitValue
    }

    private fun decreaseCounter(actualValue: Int): Int {
        val newValue = actualValue - 1
        return if (newValue > 0) newValue
        else actualValue
    }

    private fun roundToTwoDecimals(input: Float): Float {
        return DecimalFormat("#.##")
            .format(input)
            .toFloat()
    }

}
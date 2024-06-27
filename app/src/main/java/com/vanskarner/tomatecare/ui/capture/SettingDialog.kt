package com.vanskarner.tomatecare.ui.capture

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vanskarner.tomatecare.databinding.DialogCaptureSettingsBinding

internal class SettingDialog : DialogFragment() {
    private val tag = "SettingDialog"
    private lateinit var settingModel: SettingModel
    private lateinit var onClick: (setting: SettingModel) -> Unit

    fun show(fragmentManager: FragmentManager, model: SettingModel) {
        this.settingModel = model.copy()
        super.show(fragmentManager, tag)
    }

    fun setOnApply(listener: (item: SettingModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bindingSetting = DialogCaptureSettingsBinding.inflate(layoutInflater)
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setView(bindingSetting.root)
        setupView(bindingSetting)
        setupResults(bindingSetting)
        setupThreads(bindingSetting)
        return alertBuilder.create()
    }

    private fun setupView(bindingSetting: DialogCaptureSettingsBinding) {
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
        bindingSetting.spDelegate.adapter = adapterProcessors
        bindingSetting.spDelegate.setSelection(settingModel.selectedProcessorIndex)
        bindingSetting.spDelegate.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                settingModel.selectedProcessorIndex = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        bindingSetting.spModel.adapter = adapterModels
        bindingSetting.spModel.setSelection(settingModel.selectedModelIndex)
        bindingSetting.spModel.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                settingModel.selectedModelIndex = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        bindingSetting.tvMaxResults.text = settingModel.maxResults.toString()
        bindingSetting.tvThreads.text = settingModel.threads.toString()
        bindingSetting.btnApply.setOnClickListener { onClick.invoke(settingModel) }
    }

    private fun setupResults(bindingSetting: DialogCaptureSettingsBinding) {
        bindingSetting.btnLessResults.setOnClickListener {
            settingModel.maxResults =
                decreaseCounter(settingModel.maxResults)
            bindingSetting.tvMaxResults.text = settingModel.maxResults.toString()
        }
        bindingSetting.btnMoreResults.setOnClickListener {
            settingModel.maxResults =
                increaseCounter(settingModel.maxResults, settingModel.resultsLimit)
            bindingSetting.tvMaxResults.text = settingModel.maxResults.toString()
        }
    }

    private fun setupThreads(bindingSetting: DialogCaptureSettingsBinding) {
        bindingSetting.btnLessThreads.setOnClickListener {
            settingModel.threads = decreaseCounter(settingModel.threads)
            bindingSetting.tvThreads.text = settingModel.threads.toString()
        }
        bindingSetting.btnMoreThreads.setOnClickListener {
            settingModel.threads = increaseCounter(settingModel.threads, settingModel.threadLimit)
            bindingSetting.tvThreads.text = settingModel.threads.toString()
        }
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

}
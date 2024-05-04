package com.vanskarner.tomatecare.diseases

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vanskarner.tomatecare.databinding.DialogDiseaseBinding

class DiseaseDialog : DialogFragment() {
    private val tag = "DiseaseDialog"
    private lateinit var model: DiseaseDetailModel

    fun show(fragmentManager: FragmentManager, model: DiseaseDetailModel) {
        this.model = model
        super.show(fragmentManager, tag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contextDialog = requireContext()
        val binding = DialogDiseaseBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.model = model
        return AlertDialog.Builder(contextDialog)
            .setView(binding.root)
            .create()
    }

}
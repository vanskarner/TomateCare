package com.vanskarner.tomatecare.ui.capture

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vanskarner.tomatecare.databinding.DialogCaptureAdviceBinding

internal class AdvicesDialog : DialogFragment() {
    private val tag = "AdviceDialog"

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bindingAdvices = DialogCaptureAdviceBinding.inflate(layoutInflater)
        bindingAdvices.correctPlant = "correct_plant.png"
        bindingAdvices.remotePlant = "distant_plant.jpeg"
        bindingAdvices.plantVeryClose = "plant_nearby.jpg"
        bindingAdvices.blurredPlant = "unfocused_plant.jpg"
        bindingAdvices.accumulatedPlants = "accumulated_plants.jpeg"
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setView(bindingAdvices.root)
        return alertBuilder.create()
    }
}
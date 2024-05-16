package com.vanskarner.tomatecare.capture

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.databinding.DialogCaptureAdviceBinding

internal class AdvicesDialog : DialogFragment() {
    private val tag = "AdviceDialog"

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bindingAdvices = DialogCaptureAdviceBinding.inflate(layoutInflater)
        val correctPlant = requireContext().assets.open("correct_plant.png")
        val distantPlant = requireContext().assets.open("distant_plant.jpeg")
        val plantNearby = requireContext().assets.open("plant_nearby.jpg")
        val unfocusedPlant = requireContext().assets.open("unfocused_plant.jpg")
        val accumulatedPlants = requireContext().assets.open("accumulated_plants.jpeg")
        Glide.with(this)
            .load(BitmapFactory.decodeStream(correctPlant))
            .into(bindingAdvices.imvCorrectPlant)
        Glide.with(this)
            .load(BitmapFactory.decodeStream(distantPlant))
            .into(bindingAdvices.imvDistantPlant)
        Glide.with(this)
            .load(BitmapFactory.decodeStream(plantNearby))
            .into(bindingAdvices.imvPlantNearby)
        Glide.with(this)
            .load(BitmapFactory.decodeStream(unfocusedPlant))
            .into(bindingAdvices.imvBlurredPlant)
        Glide.with(this)
            .load(BitmapFactory.decodeStream(accumulatedPlants))
            .into(bindingAdvices.imvAccumulatedPlants)
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setView(bindingAdvices.root)
        return alertBuilder.create()
    }
}
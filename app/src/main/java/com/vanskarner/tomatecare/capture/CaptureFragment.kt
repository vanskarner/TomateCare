package com.vanskarner.tomatecare.capture

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.Selection
import com.vanskarner.tomatecare.databinding.DialogCaptureAdviceBinding
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding

class CaptureFragment : BaseBindingFragment<FragmentCaptureBinding>() {

    private val viewModel: CaptureViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()
    private val settingDialog = SettingDialog()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(layoutInflater)

    override fun setupView() {
        viewModelActivity.hideBottomNavigation()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { viewModelActivity.showBottomNavigation(Selection.Identification) })
        binding.btnCapture.setOnClickListener { goToIdentificationFragment() }
        binding.imvSettings.setOnClickListener { viewModel.getSetting() }
        binding.tvTips.setOnClickListener { showAdvicesDialog() }
        settingDialog.setOnApply {
            viewModel.updateSetting(it)
            settingDialog.dismiss()
        }
    }

    override fun setupViewModel() {
        viewModel.settingModel.observe(viewLifecycleOwner) {
            settingDialog.show(childFragmentManager, it)
        }
    }

    private fun goToIdentificationFragment() {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment()
        findNavController().navigate(navDirection)
    }

    private fun showAdvicesDialog() {
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
        val alert = alertBuilder.create()
        alert.show()
    }

}
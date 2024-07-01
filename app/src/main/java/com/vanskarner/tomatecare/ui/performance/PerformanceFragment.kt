package com.vanskarner.tomatecare.ui.performance

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.databinding.DialogDetectionResourcesBinding
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.databinding.FragmentPerformanceTestBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

@AndroidEntryPoint
class PerformanceFragment : BaseBindingFragment<FragmentPerformanceTestBinding>() {

    private val viewModel: PerformanceViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPerformanceTestBinding = FragmentPerformanceTestBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.imvOnBack.setOnClickListener { goToStartFragment() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
        binding.btnStart.setOnClickListener {
            val posProcessing = binding.spProcessing.selectedItemPosition
            val posModels = binding.spModel.selectedItemPosition
            viewModel.startTest(posProcessing, posModels)
        }
        binding.btnLessThreads.setOnClickListener { viewModel.decreaseThreads() }
        binding.btnMoreThreads.setOnClickListener { viewModel.increaseThreads() }
        binding.imvTestInfo1.setOnClickListener { viewModel.showTestImageForDetection() }
        binding.imvTestInfo2.setOnClickListener { viewModel.showTestImageForClassification() }
    }

    override fun setupViewModel() {
        viewModel.exampleData()
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.detectionImage.observe(viewLifecycleOwner) { showDialogTestImages(it) }
        viewModel.classificationImage.observe(viewLifecycleOwner) { showDialogTestImages(it) }
    }

    private fun showDialogTestImages(inputStream: InputStream) {
        val bindingTestResources = DialogDetectionResourcesBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingTestResources.root)
        val alertDialog = builder.create()
        bindingTestResources.imageBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        alertDialog.show()
    }

    private fun goToStartFragment() {
        val direction = PerformanceFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
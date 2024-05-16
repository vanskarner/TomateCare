package com.vanskarner.tomatecare.performance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.databinding.FragmentPerformanceTestBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }

    override fun setupViewModel() {
        viewModel.exampleData()
    }

    private fun goToStartFragment() {
        val direction = PerformanceFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
package com.vanskarner.tomatecare.ui.shareimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.databinding.FragmentShareImagesBinding
import com.vanskarner.tomatecare.ui.performance.PerformanceFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareImagesFragment : BaseBindingFragment<FragmentShareImagesBinding>() {

    private val viewModel: ShareImagesViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentShareImagesBinding = FragmentShareImagesBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.imvOnBack.setOnClickListener { goToStartFragment() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
    }

    override fun setupViewModel() {

    }

    private fun goToStartFragment() {
        val direction = PerformanceFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
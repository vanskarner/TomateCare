package com.vanskarner.tomatecare.shareimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.databinding.FragmentShareImagesBinding
import com.vanskarner.tomatecare.performance.PerformanceFragmentDirections

class ShareImagesFragment : Fragment() {
    private lateinit var binding: FragmentShareImagesBinding
    private val viewModelActivity: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShareImagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        binding.imvOnBack.setOnClickListener { goToStartFragment() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
    }

    private fun setupViewModel() {

    }

    private fun goToStartFragment() {
        val direction = PerformanceFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
package com.vanskarner.tomatecare.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding

class CaptureFragment : Fragment() {
    private lateinit var binding: FragmentCaptureBinding
    private val viewModel: CaptureViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaptureBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModelActivity.setVisibilityBottomNav(true)
        }
        binding.btnCapture.setOnClickListener { goToIdentificationFragment() }
    }

    private fun setupViewModel() {
        viewModelActivity.setVisibilityBottomNav(false)
    }

    private fun goToIdentificationFragment() {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment()
        findNavController().navigate(navDirection)
    }

}
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
import com.vanskarner.tomatecare.Selection
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
        viewModelActivity.hideBottomNavigation()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { viewModelActivity.showBottomNavigation(Selection.Identification) })
        binding.btnCapture.setOnClickListener { goToIdentificationFragment() }
    }

    private fun setupViewModel() {

    }

    private fun goToIdentificationFragment() {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment()
        findNavController().navigate(navDirection)
    }

}
package com.vanskarner.tomatecare.performance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.databinding.FragmentPerformanceTestBinding

class PerformanceFragment : Fragment() {

    private lateinit var binding: FragmentPerformanceTestBinding
    private val viewModelActivity: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerformanceTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        binding.imvOnBack.setOnClickListener { viewModelActivity.setVisibilityBottomNav(true) }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModelActivity.setVisibilityBottomNav(true)
        }
    }

    private fun setupViewModel() {

    }
}
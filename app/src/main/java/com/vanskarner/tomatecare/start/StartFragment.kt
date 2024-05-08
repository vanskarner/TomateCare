package com.vanskarner.tomatecare.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.Selection
import com.vanskarner.tomatecare.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val infoDialog = InfoDialog()
    private val viewModelActivity: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        viewModelActivity.showBottomNavigation(Selection.Start)
        binding.btnInfo.setOnClickListener { infoDialog.show(childFragmentManager) }
        binding.cardViewActivity.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToLogsFragment()
        }
        binding.cardViewTest.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToPerformanceFragment()
        }
        binding.cardViewImprove.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToShareImagesFragment()
        }
    }

    private fun setupViewModel() {

    }

    private fun goToLogsFragment() {
        val direction = StartFragmentDirections.toLogsFragment()
        findNavController().navigate(direction)
    }

    private fun goToPerformanceFragment() {
        val direction = StartFragmentDirections.toPerformanceFragment()
        findNavController().navigate(direction)
    }

    private fun goToShareImagesFragment() {
        val direction = StartFragmentDirections.toShareImagesFragment()
        findNavController().navigate(direction)
    }

}
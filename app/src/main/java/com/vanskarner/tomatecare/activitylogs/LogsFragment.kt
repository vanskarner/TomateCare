package com.vanskarner.tomatecare.activitylogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.databinding.FragmentLogsBinding


class LogsFragment : BaseBindingFragment<FragmentLogsBinding>() {

    private val viewModel:LogsViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLogsBinding = FragmentLogsBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.imvOnBack.setOnClickListener {
            goToStartFragment()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
    }

    override fun setupViewModel() {

    }

    private fun goToStartFragment() {
        val direction = LogsFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
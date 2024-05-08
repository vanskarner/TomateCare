package com.vanskarner.tomatecare.activitylogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.databinding.FragmentLogsBinding


class LogsFragment : Fragment() {
    private lateinit var binding: FragmentLogsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        binding.imvOnBack.setOnClickListener {
            goToStartFragment()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
    }

    private fun setupViewModel() {

    }

    private fun goToStartFragment() {
        val direction = LogsFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}
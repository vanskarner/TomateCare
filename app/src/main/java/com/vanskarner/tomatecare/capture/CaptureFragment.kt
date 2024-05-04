package com.vanskarner.tomatecare.capture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.MainActivityListener
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding

class CaptureFragment : Fragment() {
    private var mainActivityListener: MainActivityListener? = null
    private lateinit var binding: FragmentCaptureBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) mainActivityListener = context
    }

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

    override fun onDetach() {
        super.onDetach()
        mainActivityListener = null
    }

    private fun setupView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.startNav)
            mainActivityListener?.showBottomNav()
        }
        binding.btnCapture.setOnClickListener { goToIdentificationFragment() }
    }

    private fun setupViewModel() {

    }

    private fun goToIdentificationFragment() {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment()
        findNavController().navigate(navDirection)
    }

}
package com.vanskarner.tomatecare.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vanskarner.tomatecare.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private val infoDialog = InfoDialog()

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
        binding.btnInfo.setOnClickListener { infoDialog.show(childFragmentManager) }
        binding.cardViewActivity.setOnClickListener {
            Toast.makeText(requireContext(), "No implementado", Toast.LENGTH_SHORT).show()
        }
        binding.cardViewTest.setOnClickListener {
            Toast.makeText(requireContext(), "No implementado", Toast.LENGTH_SHORT).show()
        }
        binding.cardViewImprove.setOnClickListener {
            Toast.makeText(requireContext(), "No implementado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {

    }

}
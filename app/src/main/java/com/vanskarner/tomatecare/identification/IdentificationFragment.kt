package com.vanskarner.tomatecare.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vanskarner.tomatecare.databinding.FragmentIdentificationBinding

class IdentificationFragment : Fragment() {
    private lateinit var binding: FragmentIdentificationBinding
    private val leafAdapter = LeafAdapter()
    private val viewModel: IdentificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIdentificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = setupView()

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        binding.rcvLeaves.adapter = leafAdapter
        binding.tvSummary.setOnClickListener { showSummary() }
        binding.tvAddNote.setOnClickListener { showAddNote() }
        leafAdapter.setOnClickListener { showLeafDialog(it) }
    }

    private fun setupViewModel() {
        viewModel.exampleData()
        viewModel.identification.observe(viewLifecycleOwner) {
            binding.model = it
            showLeaves(it.leavesImage)
        }
    }

    private fun showLeaves(list: List<LeafModel>) = leafAdapter.updateList(list)

    private fun showLeafDialog(item: LeafModel) {
        Toast.makeText(requireContext(), "No implementado: $item", Toast.LENGTH_SHORT).show()
    }

    private fun showSummary() {
        Toast.makeText(requireContext(), "No implementado", Toast.LENGTH_SHORT).show()
    }

    private fun showAddNote() {
        Toast.makeText(requireContext(), "No implementado", Toast.LENGTH_SHORT).show()
    }

}

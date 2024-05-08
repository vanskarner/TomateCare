package com.vanskarner.tomatecare.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.DialogIdentificationLeafBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationNoteBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationRecommendationBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationSummaryBinding
import com.vanskarner.tomatecare.databinding.FragmentIdentificationBinding

class IdentificationFragment : Fragment() {
    private lateinit var binding: FragmentIdentificationBinding
    private val recommendationsAdapter = LeafAdapter()
    private val viewModel: IdentificationViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()
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
        binding.imvOnBack.setOnClickListener { goToCaptureFragment() }
        binding.rcvLeaves.adapter = recommendationsAdapter
        binding.tvSummary.setOnClickListener { viewModel.getSummary() }
        binding.tvAddNote.setOnClickListener { viewModel.getNote() }
        recommendationsAdapter.setOnClickListener { viewModel.getLeafInfo() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToCaptureFragment() })
    }

    private fun setupViewModel() {
        viewModel.exampleData()
        viewModel.identification.observe(viewLifecycleOwner) {
            binding.model = it
            showLeaves(it.leavesImage)
        }
        viewModel.note.observe(viewLifecycleOwner) {
            showAddNote(it) { text -> viewModel.saveNote(text) }
        }
        viewModel.summary.observe(viewLifecycleOwner) { showSummary(it) }
        viewModel.leafInfo.observe(viewLifecycleOwner) { showLeafInfoDialog(it) }
    }

    private fun goToCaptureFragment() {
        val direction = IdentificationFragmentDirections.toCaptureFragment()
        findNavController().navigate(direction)
    }

    private fun showLeaves(list: List<LeafModel>) = recommendationsAdapter.updateList(list)

    private fun showLeafInfoDialog(model: LeafInfoModel) {
        val bindingLeafInfo = DialogIdentificationLeafBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingLeafInfo.root)
        val alertDialog = builder.create()
        var visibilityPressed = true
        bindingLeafInfo.imvVisibility.setOnClickListener {
            if (visibilityPressed) {
                visibilityPressed = false
                bindingLeafInfo.imvVisibility.setImageResource(R.drawable.baseline_visibility_off_24)
                bindingLeafInfo.lyContainer.visibility = View.GONE
            } else {
                visibilityPressed = true
                bindingLeafInfo.imvVisibility.setImageResource(R.drawable.baseline_visibility_24)
                bindingLeafInfo.lyContainer.visibility = View.VISIBLE
            }
        }
        bindingLeafInfo.tvDiseaseInfo.setOnClickListener {
            alertDialog.cancel()
            goToDiseasesFragment()
        }
        bindingLeafInfo.model = model
        alertDialog.show()
    }

    private fun showSummary(summaryModel: SummaryModel) {
        val bindingSummary = DialogIdentificationSummaryBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingSummary.root)
        val alertDialog = builder.create()
        bindingSummary.model = summaryModel
        if (summaryModel.recommendations.isNotEmpty()) {
            bindingSummary.imvSummaryOk.visibility = View.GONE
            bindingSummary.btnRecommendations.visibility = View.VISIBLE
        }
        bindingSummary.btnRecommendations.setOnClickListener {
            alertDialog.cancel()
            showRecommendations(summaryModel.recommendations)
        }
        alertDialog.show()
    }

    private fun showRecommendations(list: List<RecommendationModel>) {
        val bindingRecommendations =
            DialogIdentificationRecommendationBinding.inflate(layoutInflater)
        val recommendationAdapter = RecommendationsAdapter()
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingRecommendations.root)
        val alertDialog = builder.create()
        bindingRecommendations.rcvDiseasesControl.adapter = recommendationAdapter
        recommendationAdapter.updateList(list)
        alertDialog.show()
    }

    private fun showAddNote(note: String, accept: (text: String) -> Unit) {
        val bindingNote = DialogIdentificationNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingNote.root)
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        bindingNote.edtNote.setText(note)
        bindingNote.edtNote.setSelection(bindingNote.edtNote.text.length)
        bindingNote.btnCancel.setOnClickListener { alertDialog.cancel() }
        bindingNote.btnSave.setOnClickListener {
            val text = bindingNote.edtNote.toString()
            accept.invoke(text)
            alertDialog.cancel()
        }
        alertDialog.show()
    }

    private fun goToDiseasesFragment(){
        val direction = IdentificationFragmentDirections.toDiseasesFragment()
        findNavController().navigate(direction)
    }

}

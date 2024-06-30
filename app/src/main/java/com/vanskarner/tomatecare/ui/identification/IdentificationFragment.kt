package com.vanskarner.tomatecare.ui.identification

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.DialogIdentificationLeafBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationNoteBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationRecommendationBinding
import com.vanskarner.tomatecare.databinding.DialogIdentificationSummaryBinding
import com.vanskarner.tomatecare.databinding.FragmentIdentificationBinding
import com.vanskarner.tomatecare.ui.common.OverlayView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class IdentificationFragment : BaseBindingFragment<FragmentIdentificationBinding>() {

    @Inject
    lateinit var leafAdapter: LeafAdapter

    @Inject
    lateinit var recommendationsAdapter: RecommendationsAdapter
    private val args: IdentificationFragmentArgs by navArgs()
    private val viewModel: IdentificationViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentIdentificationBinding = FragmentIdentificationBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.imvOnBack.setOnClickListener { goToCaptureFragment() }
        binding.rcvLeaves.adapter = leafAdapter
        binding.tvSummary.setOnClickListener { viewModel.getSummary() }
        binding.tvAddNote.setOnClickListener { viewModel.getNote() }
        leafAdapter.setOnClickListener { viewModel.getLeafInfo(it) }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToCaptureFragment() })
    }

    override fun setupViewModel() {
        viewModel.showAnalysis(args.idLog)
        viewModel.identification.observe(viewLifecycleOwner) {
            binding.model = it
            showLeaves(it.imgPath, it.leavesImage)
        }
        viewModel.note.observe(viewLifecycleOwner) {
            showAddNote(it) { text -> viewModel.saveNote(text) }
        }
        viewModel.summary.observe(viewLifecycleOwner) { showSummary(it) }
        viewModel.leafInfo.observe(viewLifecycleOwner) { showLeafInfoDialog(it) }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.updatedNote.observe(viewLifecycleOwner) { showToast(R.string.updated_note) }
    }

    private fun goToCaptureFragment() {
        val direction = IdentificationFragmentDirections.toCaptureFragment()
        findNavController().navigate(direction)
    }

    private fun showLeaves(imgPath: String, list: List<LeafModel>) {
        val rootImage: Bitmap? = BitmapFactory.decodeFile(imgPath)
        rootImage?.let { leafAdapter.updateList(it, list) }
    }

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
            goToDiseasesFragment(model.keyCode)
        }
        val rootImage = BitmapFactory.decodeFile(model.rootImagePath)
        val croppedImage = OverlayView.cropImageFromBoundingBox(rootImage, model.boundingBoxModel)
        bindingLeafInfo.model = model
        bindingLeafInfo.croppedImage = croppedImage
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingRecommendations.root)
        val alertDialog = builder.create()
        bindingRecommendations.rcvDiseasesControl.adapter = recommendationsAdapter
        recommendationsAdapter.updateList(list)
        alertDialog.show()
    }

    private fun showAddNote(note: String, onAccept: (text: String) -> Unit) {
        val bindingNote = DialogIdentificationNoteBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingNote.root)
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        bindingNote.edtNote.setText(note)
        bindingNote.edtNote.setSelection(bindingNote.edtNote.text.length)
        bindingNote.btnCancel.setOnClickListener { alertDialog.cancel() }
        bindingNote.btnSave.setOnClickListener {
            val text = bindingNote.edtNote.text.toString()
            onAccept.invoke(text)
            alertDialog.cancel()
        }
        alertDialog.show()
    }

    private fun goToDiseasesFragment(keyCode: String) {
        val direction = IdentificationFragmentDirections.toDiseasesFragment()
        direction.keyCode = keyCode
        findNavController().navigate(direction)
    }

}

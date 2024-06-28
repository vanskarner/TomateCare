package com.vanskarner.tomatecare.ui.identification

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Rect
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
import com.vanskarner.tomatecare.ui.common.BoundingBoxModel
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
        leafAdapter.setOnClickListener { viewModel.getLeafInfo() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToCaptureFragment() })
    }

    override fun setupViewModel() {
        viewModel.showAnalysis(args.idLog)
        viewModel.identification.observe(viewLifecycleOwner) {
            binding.model = it
            showLeaves(it.leavesImage)
        }
        viewModel.note.observe(viewLifecycleOwner) {
            showAddNote(it) { text -> viewModel.saveNote(args.idLog, text) }
        }
        viewModel.summary.observe(viewLifecycleOwner) { showSummary(it) }
        viewModel.leafInfo.observe(viewLifecycleOwner) { showLeafInfoDialog(it) }
        viewModel.error.observe(viewLifecycleOwner) { showToast(it) }
        viewModel.updatedNote.observe(viewLifecycleOwner) { showToast(R.string.updated_note) }
        viewModel.boundingBoxes.observe(viewLifecycleOwner){
            if (it.isEmpty()) binding.overlay.clear()
            else binding.overlay.setResults(it)
        }
    }

    private fun goToCaptureFragment() {
        val direction = IdentificationFragmentDirections.toCaptureFragment()
        findNavController().navigate(direction)
    }

    private fun showLeaves(list: List<LeafModel>) = leafAdapter.updateList(list)

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
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingRecommendations.root)
        val alertDialog = builder.create()
        bindingRecommendations.rcvDiseasesControl.adapter = recommendationsAdapter
        recommendationsAdapter.updateList(list)
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

    private fun goToDiseasesFragment() {
        val direction = IdentificationFragmentDirections.toDiseasesFragment()
        direction.idSelected = 1
        findNavController().navigate(direction)
    }

    private fun cropImageFromBoundingBox(imgBitmap: Bitmap, boundingBox: BoundingBoxModel): Bitmap {
        val imageWidth = imgBitmap.width
        val imageHeight = imgBitmap.height
        val x1 = boundingBox.x1 * imageWidth
        val y1 = boundingBox.y1 * imageHeight
        val x2 = boundingBox.x2 * imageWidth
        val y2 = boundingBox.y2 * imageHeight
        val croppedImageWidth = (x2 - x1).toInt()
        val croppedImageHeight = (y2 - y1).toInt()
        if (croppedImageWidth <= 0 || croppedImageHeight <= 0)
            throw IllegalArgumentException("The dimensions of the area to be trimmed cannot be <= 0")
        val rectTarget = Rect(0, 0, croppedImageWidth, croppedImageHeight)
        val croppedImage =
            createBitmap(croppedImageWidth, croppedImageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(croppedImage)
        val rect = Rect(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        canvas.drawBitmap(imgBitmap, rect, rectTarget, null)
        return croppedImage
    }

}

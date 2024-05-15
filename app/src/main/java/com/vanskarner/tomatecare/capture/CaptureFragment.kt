package com.vanskarner.tomatecare.capture

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.MainViewModel
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.Selection
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding
import java.io.File
import java.io.IOException

class CaptureFragment : BaseBindingFragment<FragmentCaptureBinding>() {

    private val viewModel: CaptureViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()
    private val settingDialog = SettingDialog()
    private val advicesDialog = AdvicesDialog()
    private val selectedImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    try {
                        val contentResolver = requireContext().contentResolver
                        val inputStream = contentResolver.openInputStream(uri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        viewModel.analyzeImage(bitmap)
                    } catch (_: Exception) {
                        showToast(R.string.image_not_loaded)
                    }
                } ?: showToast(R.string.image_not_loaded)
            }
        }
    private var currentPhotoPath: String = ""
    private val cameraRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                viewModel.analyzeImage(imageBitmap)
            } else deleteTempFile()
        }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModelActivity.hideBottomNavigation()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { viewModelActivity.showBottomNavigation(Selection.Identification) })
        binding.btnCapture.setOnClickListener { openCamera() }
        binding.imvSettings.setOnClickListener { viewModel.getSetting() }
        binding.tvTips.setOnClickListener { advicesDialog.show(childFragmentManager) }
        binding.btnPhotos.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectedImage.launch(intent)
        }
        settingDialog.setOnApply {
            viewModel.updateSetting(it)
            settingDialog.dismiss()
        }
    }

    override fun setupViewModel() {
        viewModel.settingModel.observe(viewLifecycleOwner) {
            settingDialog.show(childFragmentManager, it)
        }
        viewModel.error.observe(viewLifecycleOwner) { showError() }
        viewModel.idLog.observe(viewLifecycleOwner) { goToIdentificationFragment(it) }
        viewModel.imageToAnalyze.observe(viewLifecycleOwner) { showImageToAnalyze(it) }
    }

    private fun openCamera() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        getUriCreatedTempFile()?.let {
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, it)
            cameraRequest.launch(takePhotoIntent)
        }
    }

    private fun getUriCreatedTempFile(): Uri? {
        return try {
            val photoFile = createTempImageFile()
            if (photoFile != null && photoFile.exists())
                FileProvider.getUriForFile(
                    requireContext(),
                    getString(R.string.provider_authorities_camera),
                    photoFile
                )
            else null
        } catch (_: IOException) {
            null
        }
    }

    private fun createTempImageFile(prefix: String = "Plant"): File? {
        val imageFileName = "${prefix}_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            val image = File.createTempFile(imageFileName, ".jpg", storageDir)
            currentPhotoPath = image.absolutePath
            image
        } catch (e: IOException) {
            null
        }
    }

    private fun deleteTempFile() {
        val fileToDelete = File(currentPhotoPath)
        if (fileToDelete.exists()) fileToDelete.delete()
    }

    private fun showError() {
        binding.btnPhotos.visibility = View.VISIBLE
        binding.btnCapture.visibility = View.VISIBLE
        binding.tvTips.visibility = View.VISIBLE
        binding.imvSettings.visibility = View.VISIBLE
        binding.clIdentification.visibility = View.GONE
        binding.imvPhotoToAnalyze.setImageBitmap(null)
        showToast(R.string.error_non_analyzable_image)
    }

    private fun showImageToAnalyze(bitmap: Bitmap) {
        Glide.with(requireContext())
            .load(bitmap)
            .into(binding.imvPhotoToAnalyze)
    }

    private fun goToIdentificationFragment(idLog: Int) {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment(idLog)
        findNavController().navigate(navDirection)
    }

}
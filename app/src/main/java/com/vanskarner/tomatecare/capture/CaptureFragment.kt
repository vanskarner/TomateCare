package com.vanskarner.tomatecare.capture

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
                    val contentResolver = requireContext().contentResolver
                    try {
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        Glide.with(requireContext())
                            .load(bitmap)
                            .into(binding.imvPhotoToAnalyze)
                        viewModel.analyzeImage(bitmap)
                    } catch (_: Exception) {
                        showToast(R.string.image_not_loaded)
                    }
                } ?: showToast(R.string.image_not_loaded)
            }
        }
    private var currentPhotoPath: String? = null
    private val cameraRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
                Glide.with(requireContext())
                    .load(imageBitmap)
                    .into(binding.imvPhotoToAnalyze)
            }
        }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentCaptureBinding = FragmentCaptureBinding.inflate(layoutInflater)

    override fun setupView() {
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
        viewModel.loading.observe(viewLifecycleOwner) { showLoading() }
        viewModel.error.observe(viewLifecycleOwner) { showError() }
        viewModel.idLog.observe(viewLifecycleOwner) { goToIdentificationFragment(it) }
    }

    private fun goToIdentificationFragment(idLog: Int) {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment(idLog)
        findNavController().navigate(navDirection)
    }

    private fun showLoading() {
        binding.btnPhotos.visibility = View.GONE
        binding.btnCapture.visibility = View.GONE
        binding.tvTips.visibility = View.GONE
        binding.clIdentification.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.btnPhotos.visibility = View.VISIBLE
        binding.btnCapture.visibility = View.VISIBLE
        binding.tvTips.visibility = View.VISIBLE
        binding.clIdentification.visibility = View.GONE
        binding.imvPhotoToAnalyze.setImageBitmap(null)
        showToast(R.string.error_non_analyzable_image)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            // Maneja el error
        }
        // Continúa solo si se pudo crear el archivo
        if (photoFile != null) {
            val photoURI = FileProvider.getUriForFile(
                requireContext(),
                "com.vanskarner.tomatecare.fileprovider",
                photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraRequest.launch(takePictureIntent)
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val imageFileName = "Plant_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = image.absolutePath
        return image
    }

}
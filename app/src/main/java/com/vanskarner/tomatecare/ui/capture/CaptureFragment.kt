package com.vanskarner.tomatecare.ui.capture

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.ui.MainViewModel
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.ui.Selection
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
internal class CaptureFragment : BaseBindingFragment<FragmentCaptureBinding>() {

    @Inject
    lateinit var settingDialog: SettingDialog

    @Inject
    lateinit var advicesDialog: AdvicesDialog
    private val viewModel: CaptureViewModel by viewModels()
    private val viewModelActivity: MainViewModel by activityViewModels()
    private var currentPhotoPath: String = ""
    private val cameraRequest = registerForTakePicture { viewModel.analyzeImage2(currentPhotoPath) }
    private val galleryRequest = registerForGallery { viewModel.analyzeImage2(currentPhotoPath) }

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
        binding.btnPhotos.setOnClickListener { openGallery() }
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
    }

    private fun openCamera() {
        val photoUri = getUriCreatedTempFile()
        photoUri?.let { cameraRequest.launch(it) }
    }

    private fun openGallery() {
        galleryRequest.launch("image/*")
    }

    private fun getUriCreatedTempFile(): Uri? {
        return try {
            val photoFile = createTempImageFile()
            if (photoFile.exists())
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

    private fun createTempImageFile(prefix: String = "Plant"): File {
        val imageFileName = "${prefix}_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = image.absolutePath
        return image
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
        binding.imvPlantCover.visibility = View.VISIBLE
        binding.clIdentification.visibility = View.GONE
        binding.imvPhotoToAnalyze.setImageBitmap(null)
        binding.imvPhotoToAnalyze.visibility = View.GONE
        showToast(R.string.error_non_analyzable_image)
    }

    private fun goToIdentificationFragment(idLog: Int) {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment(idLog)
        findNavController().navigate(navDirection)
    }

    //----------
    private fun registerForTakePicture(onSuccess: () -> Unit): ActivityResultLauncher<Uri> {
        return registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) onSuccess.invoke()
            else deleteTempFile()
        }
    }

    private fun registerForGallery(onSuccess: () -> Unit): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val tempFile = createTempImageFile()
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val outputStream = tempFile.outputStream()
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                onSuccess.invoke()
            }
        }
    }

}
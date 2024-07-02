package com.vanskarner.tomatecare.ui.capture

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.ui.MainViewModel
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.ui.Selection
import com.vanskarner.tomatecare.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
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
    private val cameraRequest = registerForTakePicture {
        showImageFromPath(currentPhotoPath)
        viewModel.analyzeImage(currentPhotoPath)
    }
    private val galleryRequest = registerForGallery {
        showImageFromPath(currentPhotoPath)
        viewModel.analyzeImage(currentPhotoPath)
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
        binding.btnCapture.setOnClickListener { createTempImageUri { cameraRequest.launch(it) } }
        binding.imvSettings.setOnClickListener { viewModel.getSetting() }
        binding.tvTips.setOnClickListener { advicesDialog.show(childFragmentManager) }
        binding.btnPhotos.setOnClickListener { galleryRequest.launch("image/*") }
        settingDialog.setOnApply {
            viewModel.updateSetting(it)
            settingDialog.dismiss()
        }
    }

    override fun setupViewModel() {
        viewModel.setting.observe(viewLifecycleOwner) {
            settingDialog.show(childFragmentManager, it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            deleteTempFile()
            showError(it)
        }
        viewModel.idLog.observe(viewLifecycleOwner) { goToIdentificationFragment(it) }
        viewModel.defaultImage.observe(viewLifecycleOwner) {
            binding.imvPlantCover.setImageResource(R.drawable.plant_96)
        }
    }

    private fun registerForTakePicture(onSuccess: () -> Unit): ActivityResultLauncher<Uri> {
        return registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) onSuccess.invoke() else deleteTempFile()
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

    private fun createTempImageUri(onSuccess: (Uri) -> Unit) {
        try {
            createTempImageFile().takeIf { it.exists() }?.let { photoFile ->
                val authority = getString(R.string.provider_authorities_camera)
                onSuccess(FileProvider.getUriForFile(requireContext(), authority, photoFile))
            } ?: showToast(R.string.error_temporary_image)
        } catch (_: Exception) {
            showToast(R.string.error_temporary_image)
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

    private fun showImageFromPath(imgPath: String) {
        Glide.with(requireContext())
            .load(BitmapFactory.decodeFile(imgPath))
            .error(R.drawable.plant_96)
            .into(binding.imvPlantCover)
    }

    private fun showError(msgError: String) = showToast(msgError)

    private fun goToIdentificationFragment(idLog: Int) {
        val navDirection = CaptureFragmentDirections.toIdentificationFragment(idLog)
        navDirection.fromCapture = true
        findNavController().navigate(navDirection)
    }

}
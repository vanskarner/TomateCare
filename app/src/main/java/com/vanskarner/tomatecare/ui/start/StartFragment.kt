package com.vanskarner.tomatecare.ui.start

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.ui.MainViewModel
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.ui.Selection
import com.vanskarner.tomatecare.databinding.DialogInfoAppBinding
import com.vanskarner.tomatecare.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class StartFragment : BaseBindingFragment<FragmentStartBinding>() {

    private val viewModelActivity: MainViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStartBinding = FragmentStartBinding.inflate(layoutInflater)

    override fun setupView() {
        viewModelActivity.showBottomNavigation(Selection.Start)
        binding.btnInfo.setOnClickListener { showInfoDialog() }
        binding.cardViewActivity.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToLogsFragment()
        }
        binding.cardViewTest.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToPerformanceFragment()
        }
        binding.cardViewImprove.setOnClickListener {
            viewModelActivity.hideBottomNavigation()
            goToShareImagesFragment()
        }
    }

    override fun setupViewModel() {

    }

    private fun showInfoDialog() {
        val bindingInfo = DialogInfoAppBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bindingInfo.root)
        val alertDialog = builder.create()
        val actualContext = requireContext()
        var versionName = getString(R.string.unknown)
        try {
            val packageInfo =
                actualContext.packageManager.getPackageInfo(actualContext.packageName, 0)
            versionName = packageInfo.versionName
        } catch (_: PackageManager.NameNotFoundException) {
            val dialogMsg = getString(R.string.without_version)
            Toast.makeText(actualContext, dialogMsg, Toast.LENGTH_SHORT).show()
        }
        bindingInfo.tvAppVersion.text = getString(R.string.info_version, versionName)
        alertDialog.show()
    }

    private fun goToLogsFragment() {
        val direction = StartFragmentDirections.toLogsFragment()
        findNavController().navigate(direction)
    }

    private fun goToPerformanceFragment() {
        val direction = StartFragmentDirections.toPerformanceFragment()
        findNavController().navigate(direction)
    }

    private fun goToShareImagesFragment() {
        val direction = StartFragmentDirections.toShareImagesFragment()
        findNavController().navigate(direction)
    }

}
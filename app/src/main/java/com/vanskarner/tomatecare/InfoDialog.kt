package com.vanskarner.tomatecare

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.vanskarner.tomatecare.databinding.DialogInfoAppBinding

class InfoDialog : DialogFragment() {
    private val infoTag = "FavoritesDetailDialog"

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, infoTag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contextDialog = requireContext()
        val binding = DialogInfoAppBinding.inflate(layoutInflater)
        val appVersion = getString(R.string.info_version, getVersion(contextDialog))
        binding.tvAppVersion.text = appVersion
        return AlertDialog.Builder(contextDialog)
            .setView(binding.root)
            .create()
    }

    private fun getVersion(context: Context): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }

}
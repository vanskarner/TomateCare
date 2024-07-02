package com.vanskarner.tomatecare.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vanskarner.tomatecare.databinding.DialogLoadingBinding

abstract class BaseBindingFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T
    protected val loadingAlertDialog: AlertDialog by lazy {
        val bindingLoading = DialogLoadingBinding.inflate(layoutInflater)
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setView(bindingLoading.root)
        val alert = alertBuilder.create()
        alert.setCancelable(false)
        alert
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflateBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupView()
        setupViewModel()
    }

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    abstract fun setupView()

    abstract fun setupViewModel()

    protected fun showToast(msgId: Int) =
        Toast.makeText(requireContext(), msgId, Toast.LENGTH_SHORT).show()

    protected fun showToast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

}
package com.vanskarner.tomatecare.activitylogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.BaseBindingFragment
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.FragmentLogsBinding

class LogsFragment : BaseBindingFragment<FragmentLogsBinding>() {

    private val viewModel: LogsViewModel by viewModels()
    private val logsAdapter = LogsAdapter()
    private var selectOptions = SelectionOptions.Select

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLogsBinding = FragmentLogsBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.rcvLogs.adapter = logsAdapter
        binding.imvOnBack.setOnClickListener {
            goToStartFragment()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
        binding.lyDelete.setOnClickListener { }
        binding.btnSelect.setOnClickListener {
            when(selectOptions){
                SelectionOptions.Select -> {
                    //show all checkbox
                    binding.btnSelect.setText(R.string.select_all)
                    selectOptions = SelectionOptions.SelectAll
                }
                SelectionOptions.SelectAll -> {
                    //show all selected items
                    binding.btnSelect.setText(R.string.cancel)
                    selectOptions = SelectionOptions.Cancel
                }
                SelectionOptions.Cancel -> {
                    //hide all selections
                    binding.btnSelect.setText(R.string.select)
                    selectOptions = SelectionOptions.Select
                }
            }
        }
    }

    override fun setupViewModel() {
        viewModel.exampleList()
        viewModel.list.observe(viewLifecycleOwner) { showLogs(it) }
    }

    private fun showLogs(list: List<LogModel>) = logsAdapter.updateList(list)

    private fun goToStartFragment() {
        val direction = LogsFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

    fun showDeleteDialog(onAccept: () -> Unit) {
        val builderDialog = AlertDialog.Builder(requireContext())
        builderDialog.apply {
            setMessage(R.string.delete_selected_items)
            setPositiveButton(R.string.delete) { dialog, _ ->
                onAccept()
                dialog.dismiss()
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = builderDialog.create()
        dialog.show()
    }

}

enum class SelectionOptions {
    Select, SelectAll, Cancel
}
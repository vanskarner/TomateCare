package com.vanskarner.tomatecare.ui.activitylogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vanskarner.tomatecare.ui.common.BaseBindingFragment
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.FragmentLogsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class LogsFragment : BaseBindingFragment<FragmentLogsBinding>() {

    private val viewModel: LogsViewModel by viewModels()

    @Inject
    lateinit var logsAdapter: LogsAdapter
    private var selectOptions = SelectionOptions.Select

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLogsBinding = FragmentLogsBinding.inflate(layoutInflater)

    override fun setupView() {
        binding.rcvLogs.adapter = logsAdapter
        logsAdapter.setOnClickListener { goToIdentificationFragment(it.id) }
        binding.imvOnBack.setOnClickListener { goToStartFragment() }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressed = { goToStartFragment() })
        binding.lyDelete.setOnClickListener { viewModel.checkSelections() }
        binding.btnSelect.setOnClickListener {
            when (selectOptions) {
                SelectionOptions.Select -> showAllCheckboxes()

                SelectionOptions.SelectAll -> markAllCheckboxes()

                SelectionOptions.Cancel -> hideAllCheckboxes()
            }
        }
        binding.svLogs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.filterByNote(query)
                return false
            }
        })
    }

    override fun setupViewModel() {
        viewModel.getData()
        viewModel.list.observe(viewLifecycleOwner) { showLogs(it) }
        viewModel.noItemSelected.observe(viewLifecycleOwner) { showMsgNoItemSelected() }
        viewModel.msgDelete.observe(viewLifecycleOwner) {
            showDeleteDialog { viewModel.deleteSelectedItems() }
        }
        viewModel.restart.observe(viewLifecycleOwner) {
            binding.svLogs.setQuery("",false)
            binding.svLogs.isIconified = true
            hideAllCheckboxes()
        }
    }

    private fun showLogs(list: List<LogModel>) = logsAdapter.updateList(list)

    private fun showMsgNoItemSelected() = showToast(R.string.no_item_selected)

    private fun showDeleteDialog(onAccept: () -> Unit) {
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

    private fun showAllCheckboxes() {
        setDeleteVisibility(true)
        binding.btnSelect.setText(R.string.select_all)
        selectOptions = SelectionOptions.SelectAll
        logsAdapter.showCheckboxes()
    }

    private fun markAllCheckboxes() {
        binding.btnSelect.setText(R.string.cancel)
        selectOptions = SelectionOptions.Cancel
        logsAdapter.markAllCheckboxes()
    }

    private fun hideAllCheckboxes() {
        setDeleteVisibility(false)
        binding.btnSelect.setText(R.string.select)
        selectOptions = SelectionOptions.Select
        logsAdapter.hideCheckboxes()
    }

    private fun setDeleteVisibility(isVisible: Boolean) {
        if (isVisible) binding.lyDelete.visibility = View.VISIBLE
        else binding.lyDelete.visibility = View.GONE
    }

    private fun goToIdentificationFragment(id: Int) {
        val direction = LogsFragmentDirections.toIdentificationFragment(id)
        findNavController().navigate(direction)
    }

    private fun goToStartFragment() {
        val direction = LogsFragmentDirections.toStartFragment()
        findNavController().navigate(direction)
    }

}

internal enum class SelectionOptions {
    Select, SelectAll, Cancel
}
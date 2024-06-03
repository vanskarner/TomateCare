package com.vanskarner.tomatecare.ui.activitylogs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vanskarner.tomatecare.databinding.ItemLogBinding

internal class LogsAdapter : RecyclerView.Adapter<LogViewHolder>() {

    private var list: MutableList<LogModel> = ArrayList()
    private var onClick: (item: LogModel) -> Unit = {}
    private var checkVisibility: Boolean = false

    fun setOnClickListener(listener: (item: LogModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding = ItemLogBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LogViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item, checkVisibility, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<LogModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun markAllCheckboxes() {
        list.forEach { it.checked = true }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun hideCheckboxes() {
        list.forEach { it.checked = false }
        this.checkVisibility = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showCheckboxes() {
        this.checkVisibility = true
        notifyDataSetChanged()
    }

}

internal class LogViewHolder(private val binding: ItemLogBinding) : ViewHolder(binding.root) {

    fun bindView(item: LogModel, checkVisibility: Boolean, onClick: (item: LogModel) -> Unit) {
        binding.model = item
        binding.checkVisibility = checkVisibility
        binding.root.setOnClickListener { onClick.invoke(item) }
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
        }
    }

}
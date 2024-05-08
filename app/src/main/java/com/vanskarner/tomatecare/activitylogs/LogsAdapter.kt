package com.vanskarner.tomatecare.activitylogs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vanskarner.tomatecare.databinding.ItemLogBinding

class LogsAdapter : RecyclerView.Adapter<LogViewHolder>() {

    private var onClick: (item: LogModel) -> Unit = {}
    private var list: MutableList<LogModel> = ArrayList()

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
        holder.bindView(item, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<LogModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

class LogViewHolder(val binding: ItemLogBinding) : ViewHolder(binding.root) {
    fun bindView(item: LogModel, onClick: (item: LogModel) -> Unit) {
        binding.model = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}
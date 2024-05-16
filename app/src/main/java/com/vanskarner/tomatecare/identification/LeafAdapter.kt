package com.vanskarner.tomatecare.identification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanskarner.tomatecare.databinding.ItemLeafBinding

internal class LeafAdapter : RecyclerView.Adapter<LeafViewHolder>() {

    private var onClick: (item: LeafModel) -> Unit = {}
    private var list: MutableList<LeafModel> = ArrayList()

    fun setOnClickListener(listener: (item: LeafModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeafViewHolder {
        val binding = ItemLeafBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LeafViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: LeafViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<LeafModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

internal class LeafViewHolder(val binding: ItemLeafBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(item: LeafModel, onClick: (item: LeafModel) -> Unit) {
        binding.model = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}
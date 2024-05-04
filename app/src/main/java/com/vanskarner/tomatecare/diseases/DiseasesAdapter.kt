package com.vanskarner.tomatecare.diseases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vanskarner.tomatecare.databinding.ItemDiseaseBinding

class DiseaseAdapter : RecyclerView.Adapter<DiseaseViewHolder>() {

    private var onClick: (item: DiseaseModel) -> Unit = {}
    private var list: MutableList<DiseaseModel> = ArrayList()

    fun setOnClickListener(listener: (item: DiseaseModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val binding = ItemDiseaseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DiseaseViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<DiseaseModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

class DiseaseViewHolder(val binding: ItemDiseaseBinding) : ViewHolder(binding.root) {
    fun bindView(item: DiseaseModel, onClick: (item: DiseaseModel) -> Unit) {
        binding.model = item
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}
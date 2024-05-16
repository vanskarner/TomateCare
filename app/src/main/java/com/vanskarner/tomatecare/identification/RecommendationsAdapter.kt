package com.vanskarner.tomatecare.identification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanskarner.tomatecare.databinding.ItemDiseaseControlBinding

internal class RecommendationsAdapter : RecyclerView.Adapter<RecommendationViewHolder>() {

    private var list: MutableList<RecommendationModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemDiseaseControlBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<RecommendationModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

internal class RecommendationViewHolder(val binding: ItemDiseaseControlBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindView(item: RecommendationModel) {
        binding.model = item
    }
}
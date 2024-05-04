package com.vanskarner.tomatecare.diseases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanskarner.singleadapter.BindAdapter
import com.vanskarner.tomatecare.databinding.ItemDiseaseBinding

internal class DiseaseBindAdapter : BindAdapter<DiseaseModel, DiseaseBindAdapter.ItemViewHolder> {

    private lateinit var onClickItem: View.OnClickListener

    fun setOnClickItem(onClickItem: View.OnClickListener) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, inflater: LayoutInflater): ItemViewHolder {
        val itemBinding = ItemDiseaseBinding.inflate(inflater, parent, false)
        return ItemViewHolder.create(itemBinding, onClickItem)
    }

    override fun getClassItem(): Class<DiseaseModel> = DiseaseModel::class.java

    override fun onBindViewHolder(viewHolder: ItemViewHolder, item: DiseaseModel) {
        viewHolder.binding.diseaseModel = item
    }

    internal class ItemViewHolder private constructor(
        binding: ItemDiseaseBinding,
        onClickListener: View.OnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        val binding: ItemDiseaseBinding

        init {
            this.binding = binding
            super.itemView.tag = this
            super.itemView.setOnClickListener(onClickListener)
        }

        companion object {
            fun create(
                binding: ItemDiseaseBinding,
                onClickItem: View.OnClickListener
            ): ItemViewHolder {
                return ItemViewHolder(binding, onClickItem)
            }
        }
    }

}
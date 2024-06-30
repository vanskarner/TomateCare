package com.vanskarner.tomatecare.ui.identification

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanskarner.tomatecare.databinding.ItemLeafBinding
import com.vanskarner.tomatecare.ui.common.OverlayView

internal class LeafAdapter : RecyclerView.Adapter<LeafViewHolder>() {

    private var onClick: (item: LeafModel) -> Unit = {}
    private var list: MutableList<LeafModel> = ArrayList()
    private lateinit var rootImage: Bitmap

    fun setOnClickListener(listener: (item: LeafModel) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeafViewHolder {
        val binding = ItemLeafBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LeafViewHolder(binding, rootImage)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: LeafViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item, onClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(rootImage: Bitmap, list: List<LeafModel>) {
        this.rootImage = rootImage
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}

internal class LeafViewHolder(val binding: ItemLeafBinding, private val rootImage: Bitmap) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: LeafModel, onClick: (item: LeafModel) -> Unit) {
        binding.model = item
        binding.croppedImage =
            OverlayView.cropImageFromBoundingBox(rootImage, item.boundingBoxModel)
        binding.root.setOnClickListener { onClick.invoke(item) }
    }
}
package com.hector.galeria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
class ImagenAdapter(
    private val imagenes: IntArray,
    private val onImageClick: (Int) -> Unit
) : RecyclerView.Adapter<ImagenAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imagen, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(imagenes[position])
        holder.itemView.setOnClickListener { onImageClick(position) }
    }
    override fun getItemCount(): Int = imagenes.size
}

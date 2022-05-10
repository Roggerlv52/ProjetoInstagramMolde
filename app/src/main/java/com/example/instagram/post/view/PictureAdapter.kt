package com.example.instagram.post.view

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R

class PictureAdapter(private val onclick: (Uri) -> Unit) : RecyclerView.Adapter<PictureAdapter.PostViewHolder>() {

    var items: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])

    }
    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("NewApi")
        fun bind(image: Uri) {

            val bitmap  = itemView.context.contentResolver.loadThumbnail(image, Size(200, 200), null)
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageBitmap(bitmap)


            itemView.setOnClickListener {
                onclick.invoke(image)
            }
        }
    }

}
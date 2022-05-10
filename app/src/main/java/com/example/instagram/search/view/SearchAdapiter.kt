package com.example.instagram.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.R
import com.example.instagram.common.model.UserAuth

class SearchAdapiter(
    private val itemClick:(String)->Unit
) : RecyclerView.Adapter<SearchAdapiter.SearchViewHolder>() {
     var itens : List<UserAuth> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(itens[position])
    }

    override fun getItemCount(): Int {
        return itens.size
    }

  inner  class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserAuth) {
            itemView.findViewById<ImageView>(R.id.search_img_user).setImageURI(user.photoUri)
            itemView.findViewById<TextView>(R.id.search_txt_username).text = user.name
            itemView.setOnClickListener {
             itemClick.invoke(user.uuid)
            }
        }
    }

}

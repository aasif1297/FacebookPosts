package com.example.facebookposts.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.facebookposts.R
import com.example.facebookposts.model.PostModel
import com.example.facebookposts.model.PostsModel

class PostsAdapter(var context: Context, var list: MutableList<PostsModel>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private lateinit var view: View

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.post_img)
        var txtID: TextView = itemView.findViewById(R.id.txt_id)
        var txtMessage: TextView = itemView.findViewById(R.id.txt_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = list[position]
        holder.txtID.text = item.id
        holder.txtMessage.text = item.message

        Glide.with(holder.itemView.context)
            .load(item.full_picture)
            .into(holder.image)

    }

    override fun getItemCount() = list.size
}
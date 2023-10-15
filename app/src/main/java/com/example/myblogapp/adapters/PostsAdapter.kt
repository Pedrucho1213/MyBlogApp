package com.example.myblogapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myblogapp.R
import com.example.myblogapp.model.Posts

class PostsAdapter(private val posts: List<Posts>, private val context: Context) :
    RecyclerView.Adapter<PostsViewHolder>() {

    private val dataList = mutableListOf<Posts>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_post_item, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = posts[position]
        holder.bindView(post, context, position)
    }


}
package com.example.myblogapp.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myblogapp.databinding.CardPostItemBinding
import com.example.myblogapp.model.Posts

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var binding = CardPostItemBinding.bind(view)

    fun bindView(post: Posts, context: Context, position: Int) {
        binding.titleTxt.text = post.title
        binding.contentTxt.text = post.content
        binding.dateTxt.text = post.date
        binding.authorTxt.text = post.authorName
    }
}
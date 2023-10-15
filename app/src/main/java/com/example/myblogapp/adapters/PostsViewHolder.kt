package com.example.myblogapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myblogapp.databinding.CardPostItemBinding
import com.example.myblogapp.model.Posts
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var binding = CardPostItemBinding.bind(view)

    fun bindView(post: Posts) {
        binding.titleTxt.text = post.title
        binding.contentTxt.text = post.content
        binding.dateTxt.text = getTimeAgoString(post.date)
        binding.authorTxt.text = post.authorName
    }
    private fun getTimeAgoString(timestamp: Timestamp?): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timestampMillis = timestamp?.toDate()?.time
        val timeDifferenceMillis = currentTimeMillis - timestampMillis!!

        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis)

        if (hours >= 12) {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = Date(timestamp.toDate().time)
            return sdf.format(date)
        }

        return when {
            seconds < 60 -> "just now"
            minutes < 2 -> "a minute ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 2 -> "an hour ago"
            else -> "unknown"
        }
    }
}
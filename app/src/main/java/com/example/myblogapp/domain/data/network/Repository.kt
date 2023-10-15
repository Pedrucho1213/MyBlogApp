package com.example.myblogapp.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myblogapp.model.Posts
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class Repository {

    private val fireStore = FirebaseFirestore.getInstance()
    private val mutableData = MutableLiveData<MutableList<Posts>>()

    fun getUserData(): LiveData<MutableList<Posts>> {
        fireStore.collection("Posts").get().addOnSuccessListener {documents ->
            val listPosts = mutableListOf<Posts>()
            for (document in documents) {
                val title = document.getString("title")
                val authorName = document.getString("authorName")
                val authorId = document.getString("authorId")
                val content = document.getString("content")
                val timeStamp = document.getTimestamp("date")
                val post = Posts(title, authorName, authorId, content,timeStamp)
                listPosts.add(post)
            }
            mutableData.value = listPosts
        }
        return mutableData
    }

    fun sendPostData(post: Posts): LiveData<MutableList<Posts>> {
        fireStore.collection("Posts")
            .add(post)
            .addOnSuccessListener {
                getUserData()
            }
        return mutableData
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

}
package com.example.myblogapp.domain.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myblogapp.model.Posts
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    private val fireStore = FirebaseFirestore.getInstance()
    private val mutableData = MutableLiveData<MutableList<Posts>>()

    fun getUserData(): LiveData<MutableList<Posts>> {
        fireStore.collection("Posts").get().addOnSuccessListener {
            val listPosts = mutableListOf<Posts>()
            for (document in it) {
                val id =
                    document.getString("id")
                val title = document.getString("title")
                val authorName = document.getString("authorName")
                val authorId = document.getString("authorId")
                val content = document.getString("content")
                val date = document.getString("date")
                val post = Posts(id, title, authorName, authorId, content, date)
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

}
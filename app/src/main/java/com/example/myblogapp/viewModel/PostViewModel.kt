package com.example.myblogapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.Repository
import com.example.myblogapp.model.Posts

class PostViewModel : ViewModel() {

    private val repository = Repository()
    private val mutableData = MutableLiveData<MutableList<Posts>>()


    fun fetchPostData(): LiveData<MutableList<Posts>> {
        repository.getUserData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun sendData(post: Posts): LiveData<MutableList<Posts>> {
        repository.sendPostData(post).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

}
package com.example.myblogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.AuthFirebase
import com.example.myblogapp.domain.data.network.Repository
import com.example.myblogapp.model.Posts

class PostViewModel : ViewModel() {

    private val session = AuthFirebase()
    private val repository = Repository()
    private val mutableData = MutableLiveData<MutableList<Posts>>()
    private val signOut = MutableLiveData<Boolean>()


    fun fetchPostData(): LiveData<MutableList<Posts>> {
        repository.getAllData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchUserPosts(): LiveData<MutableList<Posts>> {
        repository.getUserPosts().observeForever {
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

    fun signOut(): MutableLiveData<Boolean> {
        session.sigOut().observeForever {
            signOut.value = it
        }
        return signOut
    }

}
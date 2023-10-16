package com.example.myblogapp.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.AuthFirebase
import com.example.myblogapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignInViewModel : ViewModel() {

    private val repository = AuthFirebase()
    private val statusSignIn = MutableLiveData<Task<AuthResult>>()
    private val credentials = MutableLiveData<User>()

    fun loginWithEmail(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        repository.loginUserWithEmail(email, password).observeForever {
            statusSignIn.value = it
        }
        return statusSignIn
    }

    fun getCredentials(email: String): MutableLiveData<User>{
        repository.getUserData(email).observeForever {
            if (it != null){
                credentials.value = it
            }
        }
        return credentials
    }



}
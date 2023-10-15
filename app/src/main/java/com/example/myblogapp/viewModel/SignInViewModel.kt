package com.example.myblogapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.AuthFirebase

class SignInViewModel : ViewModel() {

    private val repository = AuthFirebase()
    private val statusSignIn = MutableLiveData<Boolean>()

    fun loginWithEmail(email: String, password: String): MutableLiveData<Boolean> {
        repository.loginUserWithEmail(email, password).observeForever {
            statusSignIn.value = it
        }
        return statusSignIn
    }

}
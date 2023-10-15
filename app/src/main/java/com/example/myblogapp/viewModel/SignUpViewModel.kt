package com.example.myblogapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.AuthFirebase
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    private val repository = AuthFirebase()
    private val statusAuth = MutableLiveData<Boolean>()
    fun registerWithEmail(email: String, password: String): MutableLiveData<Boolean> {
        repository.registerUserWithEmail(email, password).observeForever {
            statusAuth.value = it
        }
        return statusAuth
    }
}
package com.example.myblogapp.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myblogapp.domain.data.network.AuthFirebase
import com.example.myblogapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference

class SignUpViewModel : ViewModel() {

    private val repository = AuthFirebase()
    private val statusAuth = MutableLiveData<Task<AuthResult>>()
    private val userStatus = MutableLiveData<Task<DocumentReference>>()


    fun registerWithEmail(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        repository.registerUserWithEmail(email, password).observeForever {
            statusAuth.value = it
        }
        return statusAuth
    }

    fun saveUserData(user: User): MutableLiveData<Task<DocumentReference>> {
        repository.saveUserData(user).observeForever {
            userStatus.value = it
        }
        return userStatus
    }
}
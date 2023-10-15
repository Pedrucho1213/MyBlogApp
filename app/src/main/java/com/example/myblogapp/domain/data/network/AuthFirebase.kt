package com.example.myblogapp.domain.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class AuthFirebase {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val signUpStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Boolean>()

    fun registerUserWithEmail(email: String, password: String): MutableLiveData<Boolean> {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                signUpStatus.value = true
                val user = auth.currentUser
            } else {
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.i("Error", "The user is already registered")
                    signUpStatus.value = false
                } catch (e: Exception) {
                    Log.i("Error", "An error has occurred ${e.toString()}")
                    signUpStatus.value = false

                }
                signUpStatus.value = false
            }
        }
        return signUpStatus
    }

    fun loginUserWithEmail(email: String, password: String): MutableLiveData<Boolean> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signInStatus.value = true
                    val user = auth.currentUser
                } else {
                    signInStatus.value = false
                }
            }
        return signInStatus
    }
}
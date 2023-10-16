package com.example.myblogapp.domain.data.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.Posts
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

class AuthFirebase() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val signUpStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Task<AuthResult>>()
    private val signOut = MutableLiveData<Boolean>()


    fun sigOut(): MutableLiveData<Boolean> {
        auth.signOut()
        signOut.value = true
        return signOut
    }

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

    fun loginUserWithEmail(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signInStatus.value = it
                }
            }
        return signInStatus
    }
}
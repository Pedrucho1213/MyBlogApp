package com.example.myblogapp.domain.data.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.Posts
import com.example.myblogapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class AuthFirebase() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    private val signUpStatus = MutableLiveData<Task<AuthResult>>()
    private val signInStatus = MutableLiveData<Task<AuthResult>>()
    private val signOut = MutableLiveData<Boolean>()
    private val userData = MutableLiveData<Task<DocumentReference>>()
    private val credentials = MutableLiveData<User?>()

    fun registerUserWithEmail(email: String, password: String): MutableLiveData<Task<AuthResult>> {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                signUpStatus.value = it
            } else {
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.i("Error", "The user is already registered")
                } catch (e: Exception) {
                    Log.i("Error", "An error has occurred ${e.toString()}")
                }
                signUpStatus.value = it
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

    fun saveUserData(userData: User): MutableLiveData<Task<DocumentReference>> {
        fireStore.collection("User")
            .add(userData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    this.userData.value = it
                }
            }
        return this.userData
    }

    fun getUserData(email: String): LiveData<User?> {
        fireStore.collection("User")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                val document = documents.firstOrNull()
                if (document != null) {
                    val uid = document.getString("uid")
                    val mail = document.getString("email")
                    val name = document.getString("fullName")
                    val user = User(uid, name, mail)
                    credentials.value = user
                } else {
                    credentials.value = null
                }
            }
        return credentials
    }

    fun sigOut(): MutableLiveData<Boolean> {
        auth.signOut()
        signOut.value = true
        return signOut
    }

}
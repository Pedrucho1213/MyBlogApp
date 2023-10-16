package com.example.myblogapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.ActivitySignUpBinding
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.User
import com.example.myblogapp.viewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        validateInputs()
        binding.signUpBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.emailTxt.editText?.text.toString()
        val pass = binding.confirmPassTxt.editText?.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.registerWithEmail(email, pass).observe(this) {
                if (it.isSuccessful) {
                    saveUserData(it.result.user?.uid.toString())
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    val i = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(i)
                }
            }
        }
    }

    private fun saveUserData(uid: String) {
        val email = binding.emailTxt.editText?.text.toString()
        val fullName = binding.fullNameTxt.editText?.text.toString()
        val user = User(uid, fullName, email)
        viewModel.saveUserData(user).observe(this) {
            if (it.isSuccessful) {
                PreferenceManager.saveUID(this, uid)
                PreferenceManager.saveName(this, fullName)
                PreferenceManager.saveEmail(this, email)
                Toast.makeText(
                    this,
                    "Bienvenido ${PreferenceManager.getName(this)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateInputs() {

    }
}
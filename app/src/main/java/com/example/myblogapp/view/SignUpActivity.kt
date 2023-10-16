package com.example.myblogapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.ActivitySignUpBinding
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.User
import com.example.myblogapp.viewModel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuthInvalidUserException

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
        binding.signUpBtn.setOnClickListener {
            validateInputs()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun registerUser() {
        val email = binding.emailTxt.editText?.text.toString()
        val pass = binding.confirmPassTxt.editText?.text.toString()
        viewModel.registerWithEmail(email, pass).observe(this) {
            if (it.isSuccessful) {
                saveUserData(it.result.user?.uid.toString())
                makeToast("Registered user")

            } else {
                try {
                    throw it.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    makeToast("The user is already registered")
                    Log.i("Error", "The user is already registered")
                } catch (e: Exception) {
                    makeToast("An error has occurred")
                    Log.i("Error", "An error has occurred ${e.toString()}")
                }
            }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

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
                val i = Intent(applicationContext, SignInActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    private fun validateInputs() {
        val email = binding.emailTxt.editText?.text.toString()
        val fullName = binding.fullNameTxt.editText?.text.toString()
        val pass = binding.passwordTxt.editText?.text.toString()
        val confirmPass = binding.confirmPassTxt.editText?.text.toString()

        if (email.isEmpty()) {
            binding.emailTxt.error = "Please enter an email"
        } else if (!isValidEmail(email)) {
            binding.emailTxt.error = "Please enter a valid email"
        } else {
            binding.emailTxt.error = null
        }

        if (fullName.isEmpty()) {
            binding.fullNameTxt.error = "Please enter your full name"
        } else {
            binding.fullNameTxt.error = null
        }

        if (pass.isEmpty()) {
            binding.confirmPassTxt.error = "Please enter a password"
        } else if (pass.length < 6) {
            binding.confirmPassTxt.error = "Password must be at least 6 characters long"
        } else {
            binding.confirmPassTxt.error = null
        }

        if (confirmPass.isEmpty()) {
            binding.confirmPassTxt.error = "Please confirm your password"
        } else if (pass != confirmPass) {
            binding.confirmPassTxt.error = "Passwords do not match"
        } else {
            binding.confirmPassTxt.error = null
        }

        if (binding.emailTxt.error == null && binding.fullNameTxt.error == null && binding.confirmPassTxt.error == null) {
            registerUser()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
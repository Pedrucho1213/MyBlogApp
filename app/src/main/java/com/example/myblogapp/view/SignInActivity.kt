package com.example.myblogapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.ActivitySignInBinding
import com.example.myblogapp.domain.data.PreferenceManager
import com.example.myblogapp.model.User
import com.example.myblogapp.viewModel.SignInViewModel


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        verifyLogin()
    }

    private fun verifyLogin() {
        val isLoggedIn = PreferenceManager.getLogIn(this)
        if (isLoggedIn) {
           redirectToHome()
        }
    }

    private fun redirectToHome() {
        Toast.makeText(
            this,
            "Bienvenido ${PreferenceManager.getName(this)}",
            Toast.LENGTH_SHORT
        ).show()
        val i = Intent(applicationContext, HomeActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun setListeners() {

        binding.signUpBtn.setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.emailTxt.editText?.text.toString()
        val pass = binding.passwordTxt.editText?.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.loginWithEmail(email, pass).observe(this) {
                if (it.isSuccessful) {
                    PreferenceManager.setLogIn(this, true)
                    getUserData(email)
                    redirectToHome()
                } else {
                    Toast.makeText(this, "No se encontró usuario", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getUserData(email: String) {
        viewModel.getCredentials(email).observe(this) {
            if (it != null) {
                PreferenceManager.saveUID(this, it.uid.toString())
                PreferenceManager.saveName(this, it.fullName.toString())
                PreferenceManager.saveEmail(this, email)
            }
        }
    }
}
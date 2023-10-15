package com.example.myblogapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.ActivityLoginBinding
import com.example.myblogapp.viewModel.SignInViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
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
                if (it) {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(i)
                }
            }
        }
    }
}
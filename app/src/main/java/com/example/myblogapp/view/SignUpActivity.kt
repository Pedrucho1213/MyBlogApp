package com.example.myblogapp.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myblogapp.databinding.ActivitySignUpBinding
import com.example.myblogapp.domain.data.PreferenceManager
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
            val email = binding.emailTxt.editText?.text.toString()
            val pass = binding.confirmPassTxt.editText?.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.registerWithEmail(email, pass).observe(this) {
                    if (it) {
                        PreferenceManager.saveName(
                            this,
                            binding.fullNameTxt.editText?.text.toString()
                        )
                        Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        val i = Intent(applicationContext, SignInActivity::class.java)
                        startActivity(i)
                    }
                }
            }
        }
    }

    private fun validateInputs() {

    }
}
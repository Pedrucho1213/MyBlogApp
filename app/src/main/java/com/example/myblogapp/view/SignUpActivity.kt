package com.example.myblogapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myblogapp.R
import com.example.myblogapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
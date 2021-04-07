package com.example.androidseminar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidseminar.databinding.ActivityHomeBinding
import com.example.androidseminar.databinding.ActivitySignInBinding
import com.example.androidseminar.databinding.ActivitySignUpBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvId.setText(intent.getStringExtra("userId"))
        binding.tvName.setText(intent.getStringExtra("userName"))
    }
}
package com.example.androidseminar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidseminar.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val test = "log"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "HomeActivity - onCreate")

        binding.tvId.setText(intent.getStringExtra("userId"))
        binding.tvName.setText(intent.getStringExtra("userName"))
    }

    override fun onStart() {
        super.onStart()
        Log.d(test, "HomeActivity - onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(test, "HomeActivity - onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(test, "HomeActivity - onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(test, "HomeActivity - onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(test, "HomeActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(test, "HomeActivity - onDestroy")
    }
}
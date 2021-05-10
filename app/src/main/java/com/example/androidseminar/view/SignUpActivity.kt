package com.example.androidseminar.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidseminar.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "SignUpActivity - onCreate")

        signUpEvent()

    }

    private fun signUpEvent() {
        val userName = binding.editName.text
        val userId = binding.editId.text
        val userPw = binding.editPw.text
        binding.btnSignUp.setOnClickListener {
            if (checkInputText()) {
                Toast.makeText(this, "빈 칸이 있는지 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                var intent = Intent()
                intent.putExtra("userName", userName.toString())
                intent.putExtra("userId", userId.toString())
                intent.putExtra("userPw", userPw.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun checkInputText(): Boolean{
        return binding.editName.text.isNullOrBlank() || binding.editId.text.isNullOrBlank() || binding.editPw.text.isNullOrBlank()
    }

    companion object{
        private const val test = "log"
    }

    override fun onStart() {
        super.onStart()
        Log.d(test, "SignUpActivity - onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(test, "SignUpActivity - onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(test, "SignUpActivity - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(test, "SignUpActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(test, "SignUpActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(test, "SignUpActivity - onDestroy")
    }
}
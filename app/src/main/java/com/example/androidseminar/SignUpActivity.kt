package com.example.androidseminar

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

        buttonClickEvent()

    }

    private fun buttonClickEvent() {
        val userName = binding.editName.text
        val userId = binding.editId.text
        val userPw = binding.editPw.text
        binding.btnSignUp.setOnClickListener {
            if (userName.isNullOrBlank() || userId.isNullOrBlank() || userPw.isNullOrBlank()) {
                Toast.makeText(this, "빈 칸이 있는지 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                var intent = Intent()
                intent.putExtra("userName", binding.editName.text.toString())
                intent.putExtra("userId", binding.editId.text.toString())
                intent.putExtra("userPw", binding.editPw.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
                Log.d("name",binding.editName.text.toString())
            }
        }
    }
}
package com.example.androidseminar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidseminar.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClickEvent()
    }

    private fun buttonClickEvent() {
        val userId = binding.editId.text
        val userPw = binding.editPw.text

        val prefs : SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val name = prefs.getString("name", "")
            val id = prefs.getString("id", "")
            val pw = prefs.getString("pw", "")
            if (userId.isNullOrBlank() || userPw.isNullOrBlank()) {
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show()
            } else if (userId.toString() == id && userPw.toString() == pw) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("userId",id)
                intent.putExtra("userName",name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == 100) {
            if(resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra("userName")
                val id = data?.getStringExtra("userId")
                val pw = data?.getStringExtra("userPw")

                val prefs : SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = prefs.edit()

                editor.putString("name", name)
                editor.putString("id", id)
                editor.putString("pw", pw)
//                editor.remove("name")
//                editor.remove("id")
//                editor.remove("pw")
                editor.commit()
            }
        }
    }
}
package com.example.androidseminar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.androidseminar.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "SignInActivity - onCreate")

        goToHomeActivity()
        goToSignUpActivity()
    }

    private fun goToHomeActivity() {
        binding.btnLogin.setOnClickListener {

            when {
                checkInputText() -> {
                    Toast.makeText(this, "아이디/비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show()
                }
                checkLoginInformation() -> {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToSignUpActivity(){
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            signUpActivityLauncher.launch(intent)
        }
    }
    
    private fun checkLoginInformation(): Boolean{
        val prefs : SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val id = prefs.getString("id", "")
        val pw = prefs.getString("pw", "")

        return binding.editId.text.toString() == id && binding.editPw.text.toString() == pw
    }

    private fun checkInputText(): Boolean{
        return binding.editId.text.isNullOrBlank() || binding.editPw.text.isNullOrBlank()
    }
    
    private val signUpActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val name = it.data?.getStringExtra("userName")
            val id = it.data?.getStringExtra("userId")
            val pw = it.data?.getStringExtra("userPw")

            binding.editId.setText(id)
            binding.editPw.setText(pw)

            val prefs: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()

            editor.putString("name", name)
            editor.putString("id", id)
            editor.putString("pw", pw)
            editor.commit()

            goToHomeActivity()
        }
    }

    companion object{
        private const val test = "log"
    }

    override fun onStart() {
        super.onStart()
        Log.d(test, "SignInActivity - onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(test, "SignInActivity - onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(test, "SignInActivity - onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(test, "SignInActivity - onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(test, "SignInActivity - onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(test, "SignInActivity - onStart")
    }
}
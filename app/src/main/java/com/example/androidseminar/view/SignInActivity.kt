package com.example.androidseminar.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.androidseminar.api.ServiceCreator
import com.example.androidseminar.data.SoptUserAuthStorage
import com.example.androidseminar.data.SoptUserInfo
import com.example.androidseminar.data.request.RequestLoginData
import com.example.androidseminar.data.response.ResponseLoginData
import com.example.androidseminar.databinding.ActivitySignInBinding
import com.example.androidseminar.utils.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "SignInActivity - onCreate")

        searchUserAuthStorage()
        loginCheckEvent()
        goToSignUpActivity()
    }

    private fun searchUserAuthStorage() {
        with(SoptUserAuthStorage(this)) {
            if (hasUserData()) {
                checkLoginInformation(getUserData().let { RequestLoginData(it.id, it.password) })
            }
        }
    }

    private fun loginCheckEvent() {
        binding.btnLogin.setOnClickListener {
            if (checkInputText()) {
                showToast("아이디/비밀번호를 확인해주세요!")
            } else {
                val requestLoginData = RequestLoginData(
                    email = binding.editId.text.toString(),
                    password = binding.editPw.text.toString()
                )
                checkLoginInformation(requestLoginData)
            }
        }
    }

    private fun goToSignUpActivity() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            signUpActivityLauncher.launch(intent)
        }
    }

    private fun checkLoginInformation(requestLoginData: RequestLoginData) {
        val call = ServiceCreator.soptService.postLogin(requestLoginData)
        call.enqueue(object : Callback<ResponseLoginData> {
            override fun onResponse(
                call: Call<ResponseLoginData>,
                response: Response<ResponseLoginData>
            ) {
                Log.d("test", response.code().toString() + " " + response.body()?.message)
                if (response.code() == 200) {
                    with(SoptUserAuthStorage(this@SignInActivity)) {
                        if(!hasUserData()) {
                            saveUserdata(requestLoginData.let { SoptUserInfo(it.email, it.password) })
                        }
                    }
                    val data = response.body()?.data
                    goToHomeActivity(data?.user_nickname)
                } else {
                    showToast("아이디/비밀번호가 일치하지 않습니다.")
                }
            }

            override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                Log.d("test", t.toString() + "SignIn onFailure")
            }

        })
    }

    private fun goToHomeActivity(nickName: String?) {
        showToast(nickName+"님 로그인 성공하였습니다.")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkInputText(): Boolean {
        return binding.editId.text.isNullOrBlank() || binding.editPw.text.isNullOrBlank()
    }

    private val signUpActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val id = it.data?.getStringExtra("userId")
            val pw = it.data?.getStringExtra("userPw")

            binding.editId.setText(id)
            binding.editPw.setText(pw)

            loginCheckEvent()
        }
    }

    companion object {
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
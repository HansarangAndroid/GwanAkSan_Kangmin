package com.example.androidseminar.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidseminar.api.ServiceCreator
import com.example.androidseminar.data.request.RequestSignUpData
import com.example.androidseminar.data.response.ResponseSignUpData
import com.example.androidseminar.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "SignUpActivity - onCreate")

        signUpButtonClickEvent()

    }


    private fun signUpButtonClickEvent() {
        binding.btnSignUp.setOnClickListener {
            if (checkInputText()) {
                Toast.makeText(this, "빈 칸이 있는지 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                signUpRequest()
            }
        }
    }

    private fun signUpRequest() {
        val requestSignUpData = RequestSignUpData(
            email = binding.editId.text.toString(),
            password = binding.editPw.text.toString(),
            sex = if (binding.radioSex.checkedRadioButtonId == binding.radioSexMan.id) "0" else "1",
            nickname = binding.editName.text.toString(),
            phone = binding.editPhone.text.toString(),
            birth = "${binding.datepicker.year}-${binding.datepicker.month}-${binding.datepicker.dayOfMonth}"
        )

        val call: Call<ResponseSignUpData> =
            ServiceCreator.soptService.postSignUp(requestSignUpData)

        call.enqueue(object : Callback<ResponseSignUpData> {
            override fun onResponse(
                call: Call<ResponseSignUpData>,
                response: Response<ResponseSignUpData>
            ) {
                Log.d("test", response.code().toString() + " " + response.body()?.message)
                if (response.code() == 200) {
                    successSignUp()
                } else {
                    failureSignUp()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                Log.d("test", t.toString() + "SignUp onFailure")
            }
        })
    }

    private fun successSignUp() {
        Toast.makeText(this, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show()
        Log.d("test", binding.editName.text.toString() + "텍스트")
        var intent = Intent()
        intent.putExtra("userId", binding.editId.text.toString())
        intent.putExtra("userPw", binding.editPw.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun failureSignUp() {
        Toast.makeText(this, "회원가입에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun checkInputText(): Boolean {
        return binding.editName.text.isNullOrBlank() || binding.editId.text.isNullOrBlank() || binding.editPw.text.isNullOrBlank() || binding.editPhone.text.isNullOrBlank()
    }

    companion object {
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
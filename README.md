# 1주차
<img src = "https://user-images.githubusercontent.com/56147398/115690659-38e7bd80-a398-11eb-9641-2f3d8775a255.gif" width = 20% />

- SignUpActivity
```Kotlin
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
 ```
 회원가입에서 입력한 아이디, 패스워드, 이름 값을 `putExtra`를 사용하여 `SignInActivity`로 전달<br>
 `checkInputText()` 함수를 통해 빈칸이 있는지 확인

- SignInActivity
``` Kotlin
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
```
`registerForActivityResult`의 callback을 통해 회원가입에서 보내준 아이디, 패스워드, 이름 값을 `SharedPreferences`를 사용하여 저장

``` Kotlin
    private fun checkLoginInformation(): Boolean{
        val prefs : SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val id = prefs.getString("id", "")
        val pw = prefs.getString("pw", "")

        return binding.editId.text.toString() == id && binding.editPw.text.toString() == pw
    }
```
로그인에서 입력한 값과 회원가입에서 입력한 값을 비교 후 `Boolean`타입 리턴

``` Kotlin
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
 ```
 로그인 창에서 빈칸이 있거나 아이디와 비밀번호가 일치하지 않을시 토스트 메세지 출력 <br>
 아이디와 비밀번호가 일치할 시 `HomeActivity`로 화면 전환
 
 - activity_home.xml
``` xml
        <ScrollView
            android:id="@+id/sv_introduction"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_margin="5dp"
            android:fillViewport="true"
            android:orientation="vertical"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tv_name"
            app:layout_constraintWidth_percent="0.5">

            <TextView
                android:id="@+id/tv_introduction"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/introduce" />
        </ScrollView>
```
`ScrollView`를 사용하여 자기소개가 길다면 많은 정보를 스크롤해서 볼수 있도록 구현

``` xml
    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.4" />
```
`GuideLine`을 사용하여 화면 구성

- 생명주기 로그

<img width="732" alt="KakaoTalk_Photo_2021-04-22-18-48-46" src="https://user-images.githubusercontent.com/56147398/115693981-671acc80-a39b-11eb-9636-27d608f53272.png">
세미나에서 배운 내용대로 생명주기가 호출되고 소멸되는 것을 로그로 확인할 수 있었다.<br>

**배운 내용**  
- `startActivityForResult`가 deprecated 되었고 registerForActivityResult로 대체되었다
- `constraintDimensionRatio`를 사용하여 사진 비율 조절
- xml에서 GuideLine을 사용하여 화면을 구성하는 법
- ViewBinding을 사용하여 xml을 액티비티로 가져오는 법


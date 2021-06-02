# Fourth Week Assignment
## Level1
#### Postman 테스트 사진
##### 회원가입 TEST
<img width="650" alt="스크린샷 2021-05-16 오후 6 40 40" src="https://user-images.githubusercontent.com/56147398/118393060-eee0b780-b677-11eb-8ee4-3600873ee7f4.png">

##### 로그인 TEST
<img width="650" alt="스크린샷 2021-05-16 오후 6 54 29" src="https://user-images.githubusercontent.com/56147398/118393094-2ea79f00-b678-11eb-920a-f2f46d09a7d5.png">

##### Github Api Test
<img width="650" alt="스크린샷 2021-05-11 오전 12 10 50" src="https://user-images.githubusercontent.com/56147398/118393128-6d3d5980-b678-11eb-9346-78db4a160ac5.png">

#### 회원가입, 로그인 구현 gif
<p align="center">
<img src = "https://user-images.githubusercontent.com/56147398/118393174-a4ac0600-b678-11eb-8393-f219abcc6673.gif" width = 25% />
<img src = "https://user-images.githubusercontent.com/56147398/118393175-a675c980-b678-11eb-886e-e8cd7c65376d.gif" width = 25% /> </p>

#### 로그인, 회원가입 retrofit interface
``` kotlin
interface SoptService {
    @POST("login/signin")
    fun postLogin(
        @Body body: RequestLoginData
    ): Call<ResponseLoginData>

    @POST("login/signup")
    fun postSignUp(
        @Body body: RequestSignUpData
    ): Call<ResponseSignUpData>
}
```

#### ServiceCreator
``` kotlin
object ServiceCreator {
    private const val BASE_URL = "http://cherishserver.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val soptService: SoptService = retrofit.create(SoptService::class.java)
}
```

#### SignUpActivity
``` kotlin
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
```
SoptService interface와 ServiceCreator를 사용하여 회원가입을 위해 입력한 값들을 Post방식으로 서버에 보낸 후 서버통신이 성공적이면 회원가입을 성공했다는 Toast메세지와 함께 회원가입 화면을 종료 후 다시 로그인 화면으로 돌아갑니다.

#### SignInActivity
``` kotlin
    private fun checkLoginInformation() {
        val requestLoginData = RequestLoginData(
            email = binding.editId.text.toString(),
            password = binding.editPw.text.toString()
        )
        val call = ServiceCreator.soptService.postLogin(requestLoginData)
        call.enqueue(object : Callback<ResponseLoginData> {
            override fun onResponse(
                call: Call<ResponseLoginData>,
                response: Response<ResponseLoginData>
            ) {
                Log.d("test", response.code().toString() + " " + response.body()?.message)
                if (response.code() == 200) {
                    goToHomeActivity()
                } else {
                    Toast.makeText(this@SignInActivity, "아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                Log.d("test", t.toString() + "SignIn onFailure")
            }

        })
    }

    private fun goToHomeActivity() {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
```
회원가입에서 입력했던 email과 password를 Post방식으로 서버에 보냅니다. 해당 값이 일치하여 서버와의 통신이 성공적이면 로그인 성공이라는 Toast메세지와 함께 HomeAcitvity로 화면을 전환합니다.

## Level2
<p align="center">
<img src = "https://user-images.githubusercontent.com/56147398/118394318-ec359080-b67e-11eb-8492-787537495901.png" width = 25% />
<img src = "https://user-images.githubusercontent.com/56147398/118394323-f0fa4480-b67e-11eb-9bb7-12c6e98cb731.png" width = 25% /> </p>

#### GithubApiService
``` kotlin
interface GithubApiService {
    @GET("users/{user}/repos")
    fun reposForUser(@Path("user") user: String): Call<List<GitHubRepoInfo>>

    @GET("users/kkk5474096")
    fun getUserInfo(): Call<GithubUserInfo>

    @GET("users/kkk5474096/following")
    fun getFollowingInfo(): Call<List<GithubUserInfo>>
}
```
`reposForUser()`를 사용하여 해당하는 아이디의 repository들의 대한 정보를 받아올 수 있습니다. <br>
`getUserInfo()` 를 사용하여 해당하는 아이디의 전체적인 정보를 받아올 수 있습니다. <br>
`getFollowingInfo()`를 사용하여 해당하는 아이디의 팔로워 정보를 받아올 수 있습니다. <br>
#### HomeActivity
``` kotlin
    private fun userInformation() {
        val call = GithubServiceCreater.apiService.getUserInfo()

        call.enqueue(object : Callback<GithubUserInfo> {
            override fun onResponse(
                call: Call<GithubUserInfo>,
                response: Response<GithubUserInfo>
            ) {
                Log.d(
                    "test",
                    response.code().toString() + response.body()?.login + response.body()!!.bio
                )
                if (response.code() == 200) {
                    binding.tvName.text = response.body()!!.name
                    binding.tvId.text = response.body()!!.login
                    binding.tvIntroduction.text = response.body()!!.bio
                    Glide.with(this@HomeActivity).load(response.body()!!.avatar_url)
                        .into(binding.ivProfile)
                }
            }

            override fun onFailure(call: Call<GithubUserInfo>, t: Throwable) {
                Log.d("test", t.toString() + "HomeActivity onFailure")
            }
        })
    }
```
앞서 설명했던 getUserInfo()를 사용하여 Github 해당하는 아이디에 대한 아이디와 닉네임, 자기소개, 프로필사진에 대한 정보를 받아옵니다. <br>
프로필 사진은 url로 오기 때문에 `Glide` 라이브러리를 사용하여 이미지로 변환시켜주었습니다.

``` kotlin
    private fun addAdapterList() {
        val callGetRepo = GithubServiceCreater.apiService.reposForUser("kkk5474096")

        callGetRepo.enqueue(object : Callback<List<GitHubRepoInfo>> {
            override fun onResponse(
                call: Call<List<GitHubRepoInfo>>,
                response: Response<List<GitHubRepoInfo>>
            ) {
                Log.d("결과", "성공 : ${response.raw()}")
                if (response.code() == 200) {
                    adapter.setItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<GitHubRepoInfo>>, t: Throwable) {
                Log.d("test", t.toString() + "HomeActivity onFailure")
            }
        })
    }
```
해당 아이디의 깃허브의 Repository 정보들을 받아서 recyclerView 안에 넣도록 구현했습니다.

``` kotlin
    private fun userFollowingList() {
        val call = GithubServiceCreater.apiService.getFollowingInfo()
        call.enqueue(object : Callback<List<GithubUserInfo>> {
            override fun onResponse(
                call: Call<List<GithubUserInfo>>,
                response: Response<List<GithubUserInfo>>
            ) {
                Log.d(
                    "test",
                    response.code().toString() + response.body()
                )
                if (response.code() == 200) {
                    adapter.setItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<GithubUserInfo>>, t: Throwable) {
                Log.d("test", t.toString() + "FollowingListFragment onFailure")
            }

        })
    }
```
해당 아이디의 깃허브의 Following 정보들을 받아서 recyclerView 안에 넣도록 구현했습니다.


### 배운내용
- object로 싱글턴을 구현해서 서버와 통신하는 방법을 깨달았지만 좀 더 효율적인 방법을 찾기 위해 공부해야 할 것 같습니다.
- 코드로 서버와 통신하기 전에 Postman을 사용하여 먼저 서버와 통신해볼 수 있다는 것을 배웠습니다.
- 서버통신은 많이 할수록 늘기때문에 많이 해보면서 여러 에러들을 고쳐볼 수 있도록 공부하겠습니다.


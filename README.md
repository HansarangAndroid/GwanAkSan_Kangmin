# Seventh Week Assignment
## Level1
### 자동 로그인, Logout gif
<p align="center">
<img src = "https://user-images.githubusercontent.com/56147398/120608632-5e361400-c48c-11eb-9403-27ec0edee5f2.gif" width = 25% />
<img src = "https://user-images.githubusercontent.com/56147398/120608643-63935e80-c48c-11eb-90bd-81ccc656257e.gif" width = 25% /> </p>

### Level1-1

```kotlin
    private fun searchUserAuthStorage() {
        with(SoptUserAuthStorage(this)) {
            if (hasUserData()) {
                checkLoginInformation(getUserData().let { RequestLoginData(it.id, it.password) })
            }
        }
    }
```
SignInAcitivity로 처음 들어왔을때 SharedPreference에 ID/Pw가 있다면 따로 로그인 값을 사용자가 입력할 필요 없이 해당 값으로 대체하여 로그인 과정을 건너 뜁니다.

```kotlin
    private fun checkLoginInformation(requestLoginData: RequestLoginData) {
        val call = ServiceCreator.soptService.postLogin(requestLoginData)

        call.enqueueUtil(
            onSuccess = {
                with(SoptUserAuthStorage(this@SignInActivity)) {
                    if(!hasUserData()) {
                        saveUserdata(requestLoginData.let { SoptUserInfo(it.email, it.password) })
                    }
                }
                val data = it.data
                goToHomeActivity(data?.user_nickname)
            }
        )
    }
```
사용자가 로그인에 성공하면 입력한 아이디와 패스워드 값을 SharedPreference에 집어 넣습니다.

```kotlin
    private fun logoutEvent() {
        binding.btnLogout.setOnClickListener {
            SoptUserAuthStorage(this).clearAuthStorage()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
```
HomeActivity에서 사용자가 로그아웃 버튼을 클릭하면 SharedPreference의 값들을 clear 합니다.

### Level1-2
```kotlin
class SoptUserAuthStorage(context: Context){

    private val sharedPreferences = context.getSharedPreferences(
        "${context.packageName}.$STORAGE_KEY",
        Context.MODE_PRIVATE
    )

    private val editor = sharedPreferences.edit()

    fun getUserData(): SoptUserInfo = SoptUserInfo(
        id = sharedPreferences.getString(USER_ID_KEY, "") ?: "",
        password = sharedPreferences.getString(USER_PW_KEY, "") ?: ""
    )

    fun saveUserdata(userData: SoptUserInfo) {
        editor.putString(USER_ID_KEY, userData.id)
            .putString(USER_PW_KEY, userData.password)
            .apply()
    }

    fun hasUserData(): Boolean {
        with(getUserData()) {
            return id.isNotEmpty() && password.isNotEmpty()
        }
    }

    fun clearAuthStorage() {
        editor.clear().apply()
    }

    companion object {
        private const val STORAGE_KEY = "user_auth"
        private const val USER_ID_KEY = "user_id"
        private const val USER_PW_KEY = "user_pw"
    }
}
```
SharedPreference 코드 정리입니다.

## Level2

```kotlin
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <ResponseType> Call<ResponseType>.enqueueUtil(
    onSuccess: (ResponseType) -> Unit,
    onError: ((stateCode: Int) -> Unit)? = null
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response.body() ?: return)
            } else {
                onError?.invoke(response.code())
            }
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            Log.d("NetworkTest", "error:$t")
        }

    })
}
```
Toast메시지와 서버통신 부분을 확장함수로 만들었습니다.

```kotlin
    private fun goToHomeActivity(nickName: String?) {
        showToast(nickName+"님 로그인 성공하였습니다.")
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
```
로그인에 성공하면 확장함수를 사용하여 Toast메시지를 출력하는 부분을 확인할 수 있습니다.

```kotlin
    private fun addAdapterList() {
        val callGetRepo = GithubServiceCreater.apiService.reposForUser("kkk5474096")

        callGetRepo.enqueueUtil(
            onSuccess = {
                adapter.setItems(it)
            }
        )
    }
```
서버통신을 하여 Github의 레포지토리를 가져올때에도 확장함수를 사용하여 간단하게 어뎁터에 추가할 수 있습니다.


### 배운내용
- SharedPreference를 싱글톤 패턴으로 만들어서 필요할때 사용 할 수 있다는 것을 배웠습니다.
- 확장함수를 사용하여 긴 코드를 줄일 수 있다는 것을 알았고 간단하게 확장 기능을 만들 수 있다는 것을 배웠습니다.
- 너무 많은 확장함수를 사용하면 가독성을 떨어뜨리고 유지보수를 어렵게 만들 수 있다는 것을 깨달았습니다.
- 다른 자동로그인 방법도 한번 시도해봐야겠다고 생각했습니다.

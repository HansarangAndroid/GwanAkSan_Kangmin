# Second Week Assignment
<p align="center">
<img src = "https://user-images.githubusercontent.com/56147398/115987656-f7058400-a5f0-11eb-99c6-c3e54969e010.gif" width = 25% />
<img src = "https://user-images.githubusercontent.com/56147398/115987662-fa990b00-a5f0-11eb-9371-f84036ef870c.gif" width = 25% /> </p>

### Level1
#### item_repo.xml
``` xml
    <TextView
        android:id="@+id/repo_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:maxLines="1"
        android:paddingBottom="5dp"
        android:ellipsize="end"
        android:text="레포지터리 이름"
        android:textSize="15sp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/repo_context"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:maxLines="1"
        android:ellipsize="end"
        android:text="레포지터리 설명"
        android:textSize="13sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/repo_name" />
```
`maxLine = "1"`로 설정하여 레포지터리 이름과 설명이 한줄에 표시되게 한 뒤 `ellipsize = "end"` 를 사용하여 이름이나 설명이 긴경우 뒤에... 이 나오도록 처리하였습니다.

#### HomeActivity
``` Kotlin
    private fun goToFollowingListFragment() {
        binding.btnFollowingList.setOnClickListener {
            val intent = Intent(this@HomeActivity, UserInfoActivity::class.java)
            userInfoActivityLauncher.launch(intent)
        }
    }
```
유저 정보 부분에 more란 버튼을 추가하였고 해당 버튼을 클릭 시 2차 세미나시간에 만든 FollwingListFragment를 가진 UserInfoActivity를 띄워줍니다.

#### UserInfoActivity
``` Kotlin
class UserInfoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.user_info_fragment, FollowingListFragment())
        transaction.commit()
    }
}
```
repository 정보를 담고 있는 FollwingListFragment를 가진 UserInfoActivity입니다.

### Level2
#### FollowingListFragment <br>
``` Kotlin
binding.userList.layoutManager = GridLayoutManager(activity, 2)
```
해당 코드로 과제에서 만든 아이템을 Grid로 바꾸어 줄 수 있습니다.

    private fun layoutChangeEvent(){
        binding.btnLayoutChange.setOnClickListener {
            if (currentLayout) {
                binding.userList.layoutManager = LinearLayoutManager(activity)
                currentLayout = !currentLayout
            } else {
                binding.userList.layoutManager = GridLayoutManager(activity, 2)
                currentLayout = !currentLayout
            }
        }
    }
또한 버튼을 추가해서 해당 버튼을 클릭 시 Linear형식에서 Grid형식으로 변경되거나 Grid형식에서 Linear형식으로 변경되도록 하였습니다.

#### RepoListAdapter
``` Kotlin
    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RepoViewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(repoList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwipe(position: Int) {
        repoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun afterDragAndDrop() {
        notifyDataSetChanged()
    }
    }
```
MyTouchHelperCallback에서 만든 함수들을 이용하여 adapter에 item을 길게 눌러 위치를 변경하거나 item을 옆으로 슬라이드 하면 삭제되는 기능을 넣었습니다.

#### HomeAcitivty
``` Kotlin
    private fun addAdapterList(){
        val retrofit = RetrofitClient.getInstance()
        val api = retrofit.create(GithubApiService::class.java)
        val callGetRepo = api.reposForUser("kkk5474096")

        callGetRepo.enqueue(object : Callback<List<RepoInfo>> {
            override fun onResponse(
                call: Call<List<RepoInfo>>,
                response: Response<List<RepoInfo>>
            ) {
                Log.d("결과", "성공 : ${response.raw()}")
                adapter.setItems(response.body()!!)
            }

            override fun onFailure(call: Call<List<RepoInfo>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })
    }
```
Retrofit2 라이브러리를 사용하여 Github api에 Repository를 가져오도록 구현했습니다.

### Level3
#### MyDiffUtil
``` Kotlin
class MyDiffUtil<RepoInfo>(
    private val oldItems: List<RepoInfo>,
    private val newItems: List<RepoInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem == newItem
    }
}
```

#### RepoListAdapter
``` Kotlin
    fun setItems(newItems: List<FollowingUserInfo>) {
        val diffUtil = MyDiffUtil(userList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        userList.clear()
        userList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
```
notifyDataSetChanged를 사용하면 리스트 내의 데이터가 바뀌었을때 모든 리스트를 다 바꿔야해서 아이템들이 많다면 지연시간도 길어지고 비효율적일수 밖에 없습니다.
그래서 MyDiffUtil 클래스를 만들고 DiffUtil을 사용하여 현재 리스트와 교체될 리스트를 비교하고 바꿔야할 리스트만 바꿔줌으로써 notifyDataSetChanged보다 효율적인 데이터 교환을 할 수 있게 했습니다.

### 배운내용
- 항상 notifyDataSetChanged만을 사용하였는데 DiffiUtil을 사용하여 교체될 리스트만 바꿔줄 수 있다는 것을 알았고 검색을 통해 해당 기능을 구현할 수 있게 되었습니다.
- 리사이클러뷰에서 ItemTouchHelper.Callback을 이용하여 item의 위치를 변경하거나 삭제할 수 있다는 것을 알 수 있었고 많은 공부가 되었습니다.
- Repository를 불러오는 과정에서 Retrofit2를 사용하였는데 많은 어려움이 있었지만 사용법을 깨달았습니다.


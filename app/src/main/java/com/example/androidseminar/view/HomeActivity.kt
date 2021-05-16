package com.example.androidseminar.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.androidseminar.adapter.RepoListAdapter
import com.example.androidseminar.data.GitHubRepoInfo
import com.example.androidseminar.api.GithubServiceCreater
import com.example.androidseminar.data.GithubUserInfo
import com.example.androidseminar.databinding.ActivityHomeBinding
import com.example.androidseminar.utils.MyTouchHelperCallback
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val adapter = RepoListAdapter()
    private var changeLayoutManager = false

    private val userInfoActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "HomeActivity - onCreate")

        userInformation()
        goToUserInfoActivity()
        myRepoList()
    }

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

    private fun goToUserInfoActivity() {
        binding.btnFollowingList.setOnClickListener {
            val intent = Intent(this@HomeActivity, UserInfoActivity::class.java)
            userInfoActivityLauncher.launch(intent)
        }
    }

    private fun myRepoList() {
        binding.recyclerRepo.layoutManager = LinearLayoutManager(this)
        val callback = MyTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recyclerRepo)
        binding.recyclerRepo.adapter = adapter
        adapter.startDrag(object : RepoListAdapter.OnStartDragListener {
            override fun onStartDrag(viewHolder: RepoListAdapter.RepoViewHolder) {
                touchHelper.startDrag(viewHolder)
            }
        })

        addAdapterList()
        layoutChangeEvent()

    }

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

    private fun layoutChangeEvent() {
        binding.btnLayoutChange.setOnClickListener {
            if (changeLayoutManager) {
                binding.recyclerRepo.layoutManager = LinearLayoutManager(this)
                changeLayoutManager = !changeLayoutManager
            } else {
                binding.recyclerRepo.layoutManager = GridLayoutManager(this, 2)
                changeLayoutManager = !changeLayoutManager
            }
        }
    }

    companion object {
        private const val test = "log"
    }

    override fun onStart() {
        super.onStart()
        Log.d(test, "HomeActivity - onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(test, "HomeActivity - onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(test, "HomeActivity - onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(test, "HomeActivity - onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(test, "HomeActivity - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(test, "HomeActivity - onDestroy")
    }
}
package com.example.androidseminar.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidseminar.R
import com.example.androidseminar.adapter.RepoListAdapter
import com.example.androidseminar.data.RepoInfo
import com.example.androidseminar.databinding.ActivityHomeBinding
import com.example.androidseminar.utils.MyTouchHelperCallback
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val adapter = RepoListAdapter()
    var currentLayout = false

    private val userInfoActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(test, "HomeActivity - onCreate")

        loginInformation()
        goToFollowingListFragment()
        myRepoList()
    }

    private fun loginInformation() {
        val prefs : SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val id = prefs.getString("id", "")
        val name = prefs.getString("name", "")

        binding.tvId.text = id
        binding.tvName.text = name

    }

    private fun goToFollowingListFragment() {
        binding.btnFollowingList.setOnClickListener {
            val intent = Intent(this@HomeActivity, UserInfoActivity::class.java)
            userInfoActivityLauncher.launch(intent)
        }

    }

    private fun myRepoList(){
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

    private fun addAdapterList(){
        adapter.repoList.addAll(
            listOf(
                RepoInfo(
                    name = "Algorithm",
                    description = "알고리즘 공부",
                    language = "Python"
                ),
                RepoInfo(
                    name = "Kotlin-Programming",
                    description = "Kotlin-programming",
                    language = "Kotlin"
                ),
                RepoInfo(
                    name = "MusicPlayer",
                    description = "음악 앱 실습",
                    language = "Swift"
                ),
                RepoInfo(
                    name = "IOS-programming",
                    description = "test programming",
                    language = "Swift"
                ),
                RepoInfo(
                    name = "AndroidStudy",
                    description = "2019년 2학기 앱센터 안드로이드 스터디",
                    language = "Java"
                ),
                RepoInfo(
                    name = "Mobile-Programming",
                    description = "Android-Programming",
                    language = "Java"
                )
            )
        )
        adapter.notifyDataSetChanged()
    }

    private fun layoutChangeEvent(){
        binding.btnLayoutChange.setOnClickListener {
            if (currentLayout) {
                binding.recyclerRepo.layoutManager = LinearLayoutManager(this)
                currentLayout = !currentLayout
            } else {
                binding.recyclerRepo.layoutManager = GridLayoutManager(this, 2)
                currentLayout = !currentLayout
            }
        }
    }

    companion object{
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
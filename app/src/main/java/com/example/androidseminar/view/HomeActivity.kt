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
import com.example.androidseminar.data.GithubApiService
import com.example.androidseminar.data.RepoInfo
import com.example.androidseminar.data.RetrofitClient
import com.example.androidseminar.databinding.ActivityHomeBinding
import com.example.androidseminar.utils.MyTouchHelperCallback
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val retrofit = RetrofitClient.getInstance()
        val api = retrofit.create(GithubApiService::class.java)
        val callGetRepo = api.reposForUser("kkk5474096")

        callGetRepo.enqueue(object : Callback<List<RepoInfo>> {
            override fun onResponse(
                call: Call<List<RepoInfo>>,
                response: Response<List<RepoInfo>>
            ) {
                Log.d("결과", "성공 : ${response.raw()}")
                adapter.repoList.addAll(response.body()!!)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<RepoInfo>>, t: Throwable) {
                Log.d("결과:", "실패 : $t")
            }
        })
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
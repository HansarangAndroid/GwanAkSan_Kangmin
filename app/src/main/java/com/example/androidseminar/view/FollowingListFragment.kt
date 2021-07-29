package com.example.androidseminar.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidseminar.adapter.FollowingListAdapter
import com.example.androidseminar.adapter.RepoListAdapter
import com.example.androidseminar.api.GithubServiceCreater
import com.example.androidseminar.data.GithubUserInfo
import com.example.androidseminar.databinding.FragmentFollowingListBinding
import com.example.androidseminar.utils.MyTouchHelperCallback
import com.example.androidseminar.utils.enqueueUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingListFragment : Fragment() {

    private lateinit var binding: FragmentFollowingListBinding

    private var changeLayoutManager = false

    private val adapter = FollowingListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myFollowList()

    }

    private fun myFollowList() {
        binding.recyclerUserList.layoutManager = LinearLayoutManager(activity)
        val callback = MyTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recyclerUserList)
        binding.recyclerUserList.adapter = adapter
        adapter.startDrag(object : RepoListAdapter.OnStartDragListener {
            override fun onStartDrag(viewHolder: RepoListAdapter.RepoViewHolder) {
                touchHelper.startDrag(viewHolder)
            }
        })

        userFollowingList()
        layoutChangeEvent()
    }

    private fun userFollowingList() {
        val call = GithubServiceCreater.apiService.getFollowingInfo()

        call.enqueueUtil(
            onSuccess = {
                adapter.setItems(it)
            }
        )
    }

    private fun layoutChangeEvent() {
        binding.btnLayoutChange.setOnClickListener {
            if (changeLayoutManager) {
                binding.recyclerUserList.layoutManager = LinearLayoutManager(activity)
                changeLayoutManager = !changeLayoutManager
            } else {
                binding.recyclerUserList.layoutManager = GridLayoutManager(activity, 2)
                changeLayoutManager = !changeLayoutManager
            }
        }
    }
}

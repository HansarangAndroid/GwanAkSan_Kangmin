package com.example.androidseminar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidseminar.adapter.FollowingListAdapter
import com.example.androidseminar.adapter.RepoListAdapter
import com.example.androidseminar.data.FollowingUserInfo
import com.example.androidseminar.databinding.FragmentFollowingListBinding
import com.example.androidseminar.utils.MyTouchHelperCallback


class FollowingListFragment : Fragment() {

    lateinit var binding: FragmentFollowingListBinding
    var currentLayout = false
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


        binding.userList.layoutManager = LinearLayoutManager(activity)
        val callback = MyTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.userList)
        binding.userList.adapter = adapter
        adapter.startDrag(object : RepoListAdapter.OnStartDragListener {
            override fun onStartDrag(viewHolder: RepoListAdapter.RepoViewHolder) {
                touchHelper.startDrag(viewHolder)
            }
        })

        addAdapterList()
        layoutChangeEvent()
    }
    
    private fun addAdapterList(){
        adapter.userList.addAll(
            listOf(
                FollowingUserInfo(
                    userName = "SONPYEONGHWA"
                ),
                FollowingUserInfo(
                    userName = "kkk5474096"
                ),
                FollowingUserInfo(
                    userName = "Jionee"
                ),
                FollowingUserInfo(
                    userName = "ssonghyun101"
                )
            )
        )
        adapter.notifyDataSetChanged()
    }

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
}

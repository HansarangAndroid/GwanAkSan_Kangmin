package com.example.androidseminar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidseminar.databinding.ItemFollowUserBinding

class FollowingListAdapter : RecyclerView.Adapter<FollowingListAdapter.FollowingUserViewHolder>() {

    val userList = mutableListOf<FollowingUserInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingListAdapter.FollowingUserViewHolder {
        val binding= ItemFollowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingUserViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: FollowingUserViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    class FollowingUserViewHolder(
        private val binding: ItemFollowUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(followingUserInfo : FollowingUserInfo) {
            binding.followUserName.text = followingUserInfo.userName
        }
    }
}
package com.example.androidseminar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidseminar.data.GithubUserInfo
import com.example.androidseminar.databinding.ItemFollowUserBinding
import com.example.androidseminar.utils.MyDiffUtil
import com.example.androidseminar.utils.MyTouchHelperCallback
import java.util.*

class FollowingListAdapter : RecyclerView.Adapter<FollowingListAdapter.FollowingUserViewHolder>(),
    MyTouchHelperCallback.OnItemMoveListener {

    val userList = mutableListOf<GithubUserInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingListAdapter.FollowingUserViewHolder {
        val binding =
            ItemFollowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingUserViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RepoListAdapter.RepoViewHolder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(userList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwipe(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun afterDragAndDrop() {
        notifyDataSetChanged()
    }

    fun setItems(newItems: List<GithubUserInfo>) {
        val diffUtil = MyDiffUtil(userList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        userList.clear()
        userList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: FollowingUserViewHolder, position: Int) {
        holder.onBind(userList[position], holder.itemView.context)
    }

    class FollowingUserViewHolder(
        private val binding: ItemFollowUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(followingUserInfo: GithubUserInfo, context: Context) {
            binding.followUserName.text = followingUserInfo.login
            Glide.with(context).load(followingUserInfo.avatar_url).into(binding.followUserImage)
        }
    }
}
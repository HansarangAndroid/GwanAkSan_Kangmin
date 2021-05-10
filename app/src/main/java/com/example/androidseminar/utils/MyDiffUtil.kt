package com.example.androidseminar.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.androidseminar.data.FollowingUserInfo
import com.example.androidseminar.data.RepoInfo

class MyDiffUtil<RepoInfo>(
    private val oldItems: List<RepoInfo>,
    private val newItems: List<RepoInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem.hashCode() == newItem.hashCode()

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem == newItem
    }
}
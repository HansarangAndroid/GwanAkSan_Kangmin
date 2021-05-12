package com.example.androidseminar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidseminar.data.GitHubRepoInfo
import com.example.androidseminar.databinding.ItemRepoBinding
import com.example.androidseminar.utils.MyDiffUtil
import com.example.androidseminar.utils.MyTouchHelperCallback
import java.util.*

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(),
    MyTouchHelperCallback.OnItemMoveListener {

    private val repoList = mutableListOf<GitHubRepoInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int = repoList.size

    fun setItems(newItems: List<GitHubRepoInfo>) {
        val diffUtil = MyDiffUtil(repoList, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        repoList.clear()
        repoList.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

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

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(repoList[position])
    }

    class RepoViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(gitHubRepoInfo: GitHubRepoInfo) {
            binding.apply {
                repoName.text = gitHubRepoInfo.name
                repoContext.text = gitHubRepoInfo.description
                repoLanguage.text = gitHubRepoInfo.language
            }
        }
    }
}
package com.example.androidseminar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidseminar.databinding.ItemRepoBinding
import java.util.*

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(), MyTouchHelperCallback.OnItemMoveListener {

    val repoList = mutableListOf<RepoInfo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoListAdapter.RepoViewHolder {
        val binding= ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int = repoList.size

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
        fun onBind(repoInfo : RepoInfo) {
            binding.repoName.text = repoInfo.repoName
            binding.repoContext.text = repoInfo.repoContext
            binding.repoLanguage.text = repoInfo.repoLanguage
        }
    }
}
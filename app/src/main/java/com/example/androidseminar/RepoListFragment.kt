package com.example.androidseminar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidseminar.databinding.FragmentRepoListBinding


class RepoListFragment : Fragment() {

    lateinit var binding: FragmentRepoListBinding
    var currentLayout = false
    private val adapter = RepoListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerRepo.layoutManager = LinearLayoutManager(activity)
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
                    repoName = "지금은 빈칸!",
                    repoContext = "kangmin",
                    repoLanguage = "c"
                ),
                RepoInfo(
                    repoName = "지금은 빈칸!",
                    repoContext = "kangmin",
                    repoLanguage = "c"
                ),
                RepoInfo(
                    repoName = "지금은 빈칸!",
                    repoContext = "kangmin",
                    repoLanguage = "c"
                ),
                RepoInfo(
                    repoName = "지금은 빈칸!",
                    repoContext = "kangmin",
                    repoLanguage = "c"
                ),
                RepoInfo(
                    repoName = "지금은 빈칸!",
                    repoContext = "kangmin",
                    repoLanguage = "c"
                )
            )
        )
        adapter.notifyDataSetChanged()
    }

    private fun layoutChangeEvent(){
        binding.btnLayoutChange.setOnClickListener {
            if (currentLayout) {
                binding.recyclerRepo.layoutManager = LinearLayoutManager(activity)
                currentLayout = !currentLayout
            } else {
                binding.recyclerRepo.layoutManager = GridLayoutManager(activity, 2)
                currentLayout = !currentLayout
            }
        }
    }
}

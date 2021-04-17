package com.example.androidseminar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidseminar.databinding.FragmentFollowingListBinding


class FollowingListFragment : Fragment() {

    lateinit var binding: FragmentFollowingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followingListAdapter = FollowingListAdapter()

        binding.userList.adapter = followingListAdapter

        followingListAdapter.userList.addAll(
            listOf(
                FollowingUserInfo(
                    userImage = "지금은 빈칸!",
                    userName = "kangmin"
                ),
                FollowingUserInfo(
                    userImage = "지금은 빈칸!",
                    userName = "nanana"
                ),
                FollowingUserInfo(
                    userImage = "지금은 빈칸!",
                    userName = "qwerqwer"
                ),
                FollowingUserInfo(
                    userImage = "지금은 빈칸!",
                    userName = "kang"
                )

            )
        )
        followingListAdapter.notifyDataSetChanged()
    }
}

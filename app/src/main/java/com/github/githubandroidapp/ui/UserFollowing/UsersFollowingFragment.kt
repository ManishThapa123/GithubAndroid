package com.github.githubandroidapp.ui.UserFollowing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.api.models.entities.Item
import com.github.githubandroidapp.R
import com.github.githubandroidapp.databinding.FragmentUsersFollowingBinding
import com.github.githubandroidapp.helper.AppConstants
import com.github.githubandroidapp.helper.toast
import com.github.githubandroidapp.ui.SearchUser.SearchUserRecyclerViewAdapter
import com.github.githubandroidapp.viewmodel.UsersFollowingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFollowingFragment : Fragment() {

    private lateinit var binding: FragmentUsersFollowingBinding
    private val viewModel: UsersFollowingViewModel by viewModels()
    private lateinit var searchUserRecyclerViewAdapter: SearchUserRecyclerViewAdapter
    private var usersProfileList: ArrayList<Item>? = ArrayList()
    private var userName: String? = null
    private var section: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSafeArgValues()
        observeViewModel()
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUsersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterView()
        setSectionsName(section)

        userName?.let { getProfiles(section ?: AppConstants.FOLLOWING_BUTTON_CLICKED, it) }
    }

    private fun setAdapter() {
        searchUserRecyclerViewAdapter = SearchUserRecyclerViewAdapter {
            openUserProfile(it)
        }
    }

    private fun setAdapterView() {
        binding.recyclerFollowingsView.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = searchUserRecyclerViewAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            userList.observe({ lifecycle }) { usersList ->
                if (usersList!!.isEmpty()) {
                    makeRecyclerViewVisible(false)
                } else {
                    makeRecyclerViewVisible(true)
                }
                usersProfileList?.addAll(usersList)
                searchUserRecyclerViewAdapter.submitList(usersList)
            }

            responseFailure.observe({lifecycle}){
                if (it){
                    requireContext().toast("Whoops! Something went wrong! Please retry.")
                }
            }
        }
    }

    private fun makeRecyclerViewVisible(visible: Boolean) {
        when {
            visible -> {
                binding.noItemsImg.visibility = View.GONE
                binding.recyclerFollowingsView.visibility = View.VISIBLE
            }
            !visible -> {
                binding.recyclerFollowingsView.visibility = View.GONE
                binding.noItemsImg.visibility = View.VISIBLE
            }
        }
    }

    private fun setSectionsName(section: String?) {
        when (section) {
            AppConstants.FOLLOWING_BUTTON_CLICKED -> binding.headerText.text =
                getString(R.string.following_text)
            AppConstants.FOLLOWER_BUTTON_CLICKED -> binding.headerText.text =
                getString(R.string.followers_text)
        }
    }

    private fun openUserProfile(user: String) {
        val action =
            UsersFollowingFragmentDirections.actionUsersFollowingFragmentToUserProfileFragment(
                user, AppConstants.FOLLOW_SECTION)
        findNavController().navigate(action)
    }

    private fun getSafeArgValues() {
        arguments.let {
            userName = it?.getString("userName").toString()
            section = it?.getString("section").toString()
        }
    }

    /**
     * Get profiles based on section type @see [section].
     */
    private fun getProfiles(section: String, userName: String) {
        when (section) {
            AppConstants.FOLLOWER_BUTTON_CLICKED -> {
                viewModel.getFollowers(userName)
            }
            AppConstants.FOLLOWING_BUTTON_CLICKED -> {
                viewModel.getFollowing(userName)
            }
        }
    }
}
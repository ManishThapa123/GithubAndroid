package com.github.githubandroidapp.ui.UserProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.api.models.response.UserProfileResponse
import com.github.githubandroidapp.databinding.FragmentUserProfileBinding
import com.github.githubandroidapp.helper.AppConstants
import com.github.githubandroidapp.helper.loadImage
import com.github.githubandroidapp.helper.toast
import com.github.githubandroidapp.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel: UserProfileViewModel by viewModels()
    private var userId: String? = null
    private var section: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSafeArgValues()
        observeViewModel()
    }

    private fun getSafeArgValues() {
        arguments.let {
            userId = it?.getString("userName").toString()
            section = it?.getString("section").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId?.let { getUserProfile(it) }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnFollowers.setOnClickListener {
                val action =
                    UserProfileFragmentDirections.actionUserProfileFragmentToUsersFollowingFragment(
                        userId, AppConstants.FOLLOWER_BUTTON_CLICKED)
                findNavController().navigate(action)
            }
            btnFollowing.setOnClickListener {
                val action =
                    UserProfileFragmentDirections.actionUserProfileFragmentToUsersFollowingFragment(
                        userId, AppConstants.FOLLOWING_BUTTON_CLICKED)
                findNavController().navigate(action)
            }
        }
    }

    private fun getUserProfile(userId: String) {
        viewModel.getGitHubUserProfile(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun observeViewModel() {
        viewModel.apply {
            userProfile.observe({ lifecycle }) { userProfile ->
                if (userProfile != null) {
                    setProfileValues(userProfile)
                }
            }
            responseFailure.observe({lifecycle}){
                if (it){
                    requireContext().toast("Whoops! Something went wrong! Please retry.")
                }
            }
        }
    }

    private fun setProfileValues(userProfile: UserProfileResponse) {
        binding.apply {
            when (section) {
                AppConstants.FOLLOW_SECTION -> {
                    lLFollowings.visibility = View.GONE
                }
                else ->{
                    lLFollowings.visibility = View.VISIBLE
                }
            }
            personName.text = userProfile.name ?: "Unknown"
            txtUserName.text = userProfile.login
            txtDescription.text = userProfile.bio ?: "Not set"
            btnFollowers.text = "${userProfile.followers} Followers"
            btnFollowing.text = "${userProfile.following} Following"
            imgAvatar.loadImage("${userProfile.avatarUrl}")

        }
    }
}
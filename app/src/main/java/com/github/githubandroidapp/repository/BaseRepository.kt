package com.github.githubandroidapp.repository

import com.github.api.GithubAPIInterface
import com.github.api.models.entities.Item
import com.github.api.models.response.SearchUserResponse
import com.github.api.models.response.UserProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject


class BaseRepository @Inject constructor(
    private val apiService: GithubAPIInterface
) {

    suspend fun searchGitHubUser(userName: String?): Response<SearchUserResponse>{
        return apiService.searchUsers(userName)
    }

    suspend fun getProfile(userName: String?): Response<UserProfileResponse>{
        return apiService.getUserDetails(userName)
    }

    suspend fun getFollowers(userName: String?): Response<List<Item>>{
        return apiService.getFollowers(userName)
    }

    suspend fun getFollowing(userName: String?): Response<List<Item>>{
        return apiService.getFollowing(userName)
    }
}
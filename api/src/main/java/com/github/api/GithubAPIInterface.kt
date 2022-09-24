package com.github.api

import com.github.api.models.entities.Item
import com.github.api.models.response.SearchUserResponse
import com.github.api.models.response.UserProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIInterface {

    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun searchUsers(
        @Query("q") userName: String?, ): Response<SearchUserResponse>

    @GET("user/{username}")
    @Headers("Authorization: token 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")
    suspend fun getUserDetails(
        @Path("username") userName: String?, ): Response<UserProfileResponse>

    @GET("user/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(
        @Path("username") userName: String?, ): Response<List<Item>>

    @GET("user/{username}/following")
    @Headers("Authorization: token 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")
    suspend fun getFollowing(
        @Path("username") userName: String?, ): Response<List<Item>>
}
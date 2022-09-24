package com.github.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object GithubClient {

    private const val API_PREFIX = "https://api.github.com/"

    /**
     * The Retrofit class generates an implementation of the [GithubAPIInterface] interface.
     */
    // Set the custom client when building adapter
    private val okHttpBuilder = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_PREFIX)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpBuilder)
        .build()

    var service: GithubAPIInterface = retrofit.create(GithubAPIInterface::class.java)
}
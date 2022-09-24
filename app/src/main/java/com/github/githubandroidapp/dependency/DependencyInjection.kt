package com.github.githubandroidapp.dependency

import com.github.api.GithubAPIInterface
import com.github.api.GithubClient
import com.github.githubandroidapp.repository.BaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object DependencyInjection {

        /**
         * provides instance of api client interface [GithubClient.service]
         */
        @Provides
        @Singleton
        fun provideApiClient(): GithubAPIInterface = GithubClient.service

        /**
         * provides instance of base repository [BaseRepository]
         */
        @Provides
        @Singleton
        fun providesBaseRepository(
            apiInterface: GithubAPIInterface): BaseRepository =
            BaseRepository(apiInterface)

    }

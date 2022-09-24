package com.github.githubandroidapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.api.models.entities.Item
import com.github.api.models.response.UserProfileResponse
import com.github.githubandroidapp.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val baseRepository: BaseRepository) :
    ViewModel() {

    private val _userProfile = MutableLiveData<UserProfileResponse?>()
    val userProfile: LiveData<UserProfileResponse?> = _userProfile

    private val _responseFailure = MutableLiveData<Boolean>()
    val responseFailure: LiveData<Boolean> = _responseFailure

    fun getGitHubUserProfile(userName: String?) = viewModelScope.launch {
        baseRepository.getProfile(userName).let { response ->
            when {
                response.isSuccessful -> {
                    val user = response.body()
                    _userProfile.value = user
                }
                else -> {
                    _responseFailure.value = true
                }
            }
        }
    }
}
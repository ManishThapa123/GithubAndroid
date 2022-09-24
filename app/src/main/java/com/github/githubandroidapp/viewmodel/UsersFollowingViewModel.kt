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
class UsersFollowingViewModel @Inject constructor(
    private val baseRepository: BaseRepository) : ViewModel() {

    private val _userList = MutableLiveData<List<Item>?>()
    val userList: LiveData<List<Item>?> = _userList

    private val _responseFailure = MutableLiveData<Boolean>()
    val responseFailure: LiveData<Boolean> = _responseFailure

    fun getFollowers(userName: String?) = viewModelScope.launch {
        baseRepository.getFollowers(userName).let { response ->
            when {
                response.isSuccessful -> {
                    val listOfUsers = response.body()
                    _userList.value = listOfUsers
                }
                else -> {
                    _responseFailure.value = true
                }
            }
        }
    }

    fun getFollowing(userName: String?) = viewModelScope.launch {
        baseRepository.getFollowing(userName).let { response ->
            when {
                response.isSuccessful -> {
                    val listOfUsers = response.body()
                    _userList.value = listOfUsers
                }
                else -> {
                    _responseFailure.value = true
                }
            }
        }
    }
}
package com.github.githubandroidapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.api.models.entities.Item
import com.github.githubandroidapp.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(private val baseRepository: BaseRepository) :
    ViewModel() {

    private val _usersList = MutableLiveData<List<Item>?>()
    val usersList: LiveData<List<Item>?> = _usersList

    private val _responseFailure = MutableLiveData<Boolean>()
    val responseFailure: LiveData<Boolean> = _responseFailure

    fun searchGitHubUser(userName: String?) = viewModelScope.launch {
        baseRepository.searchGitHubUser(userName).let { response ->
            when {
                response.isSuccessful -> {
                    val listOfUsers = response.body()?.items
                    _usersList.value = listOfUsers
                }
                else -> {
                    _responseFailure.value = true
                }
            }
        }
    }
}
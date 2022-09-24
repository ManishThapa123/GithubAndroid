package com.github.githubandroidapp.ui.SearchUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.api.models.entities.Item
import com.github.githubandroidapp.databinding.FragmentSearchUserBinding
import com.github.githubandroidapp.helper.toast
import com.github.githubandroidapp.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private val viewModel: SearchUserViewModel by viewModels()
    private lateinit var searchUserRecyclerViewAdapter: SearchUserRecyclerViewAdapter
    private var usersProfileList: ArrayList<Item>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterView()
        setSearchListener()
        checkIfDataExists()
    }

    private fun setAdapter() {
        searchUserRecyclerViewAdapter = SearchUserRecyclerViewAdapter {
            openUserProfile(it)
        }
    }

    private fun setSearchListener() {
        binding.apply {
            searchView.apply {
                imeOptions = EditorInfo.IME_ACTION_SEARCH
                isSubmitButtonEnabled = true
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        displayShimmer(true)
                        viewModel.searchGitHubUser(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }
        }
    }

    private fun setAdapterView() {
        binding.recyclerSearchView.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = searchUserRecyclerViewAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            usersList.observe({ lifecycle }) { usersList ->

                if (usersList!!.isEmpty()) {
                    makeRecyclerViewVisible(false)
                } else {
                    makeRecyclerViewVisible(true)
                }
                usersProfileList?.addAll(usersList)
                searchUserRecyclerViewAdapter.submitList(usersList)
                displayShimmer(false)
            }

            responseFailure.observe({ lifecycle }) {
                if (it) {
                    displayShimmer(false)
                    requireContext().toast("Whoops! Something went wrong! Please retry.")
                    makeRecyclerViewVisible(false)
                }
            }
        }
    }

    private fun makeRecyclerViewVisible(visible: Boolean) {
        when {
            visible -> {
                binding.noItemsImg.visibility = View.GONE
                binding.recyclerSearchView.visibility = View.VISIBLE
            }
            !visible -> {
                binding.recyclerSearchView.visibility = View.GONE
                binding.noItemsImg.visibility = View.VISIBLE
            }
        }
    }

    private fun checkIfDataExists() {
        if (usersProfileList!!.isNotEmpty()) {
            searchUserRecyclerViewAdapter.submitList(usersProfileList)
            makeRecyclerViewVisible(true)
        }
    }

    private fun openUserProfile(user: String) {
        //Open User Profile
        val action =
            SearchUserFragmentDirections.actionSearchUserFragmentToUserProfileFragment(
                user
            )
        findNavController().navigate(action)
    }

    private fun displayShimmer(show: Boolean) {
        when (show) {
            true -> {
                binding.recyclerSearchView.visibility = View.GONE
                binding.noItemsImg.visibility = View.GONE
                binding.shimmerViewContainer.startShimmer()
                binding.shimmerViewContainer.visibility = View.VISIBLE
            }
            false -> {
                binding.shimmerViewContainer.visibility = View.GONE
                binding.shimmerViewContainer.stopShimmer()
            }
        }
    }

    override fun onPause() {
        displayShimmer(false)
        super.onPause()
    }

}
package com.github.githubandroidapp.ui.SearchUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.api.models.entities.Item
import com.github.githubandroidapp.databinding.ItemUserListBinding
import com.github.githubandroidapp.helper.loadImage

 class SearchUserRecyclerViewAdapter (val onProfileClicked : (clickedUser: String) -> Unit):
    ListAdapter<Item, SearchUserRecyclerViewAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return "${oldItem.id}" == "${newItem.id}"
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return "${oldItem.id}" == "${newItem.id}"
        }

    }) {

    inner class ViewHolder(val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userData: Item) {
            setUserData(userData, binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = getItem(position)
        holder.bind(userData)
        holder.binding.root.setOnClickListener {
            onProfileClicked("${userData.id}")
        }
    }

    private fun setUserData(userData: Item, binding: ItemUserListBinding) {
        binding.apply {
            personName.text = "${userData.login}"
            imgAvatar.loadImage("${userData.avatarUrl}")
            txtURL.text = "${userData.login}"
        }
    }


}
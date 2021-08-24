package com.example.githubauthorization.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubauthorization.R
import com.example.githubauthorization.databinding.ItemRepositoryBinding
import com.example.githubauthorization.db.EntityRepo


class AdapterFavoriteRepository: ListAdapter<EntityRepo, AdapterFavoriteRepository.FavoriteRepositoryViewHolder> (FavoriteDiffUtil()){

    class FavoriteRepositoryViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityRepo) {
            binding.repoName.text = item.name
            binding.userName.text = item.login
            binding.description.text = item.description
            binding.imageView.load(item.avatar_url){
                placeholder(R.drawable.ic_account)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteRepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteRepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteRepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class FavoriteDiffUtil: DiffUtil.ItemCallback<EntityRepo>() {
    override fun areItemsTheSame(oldItem: EntityRepo, newItem: EntityRepo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EntityRepo, newItem: EntityRepo): Boolean {
        return oldItem == newItem
    }
}
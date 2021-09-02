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
import com.example.githubauthorization.databinding.ItemFavoriteRepositoryBinding
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.models.Item


class AdapterFavoriteRepository(
    private val itemClick: (EntityRepo) -> Unit, private val starClick: (EntityRepo) -> Unit
) : ListAdapter<EntityRepo, AdapterFavoriteRepository.FavoriteRepositoryViewHolder> (FavoriteDiffUtil()){

    class FavoriteRepositoryViewHolder(val binding: ItemFavoriteRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
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
        val view = ItemFavoriteRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteRepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteRepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClick(getItem(position))
        }

        holder.binding.starFavorite.setOnClickListener {
            starClick(getItem(position))
        }
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
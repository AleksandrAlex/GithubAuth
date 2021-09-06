package com.example.githubauthorization.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubauthorization.R
import com.example.githubauthorization.databinding.ItemRepositoryBinding
import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ItemHolder

class AdapterRepository(private val itemClick: (ItemHolder) -> Unit): PagingDataAdapter<ItemHolder, AdapterRepository.ItemViewHolder>(ItemDiffUtil()) {

    class ItemViewHolder(val binding: ItemRepositoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemHolder) {
            binding.repoName.text = item.name
            binding.userName.text = item.owner.login
            binding.description.text = item.description
            binding.imageView.load(item.owner.avatar_url){
                placeholder(R.drawable.ic_account)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> itemClick(it1) }
        }
    }
}

class ItemDiffUtil: DiffUtil.ItemCallback<ItemHolder>() {
    override fun areItemsTheSame(oldItem: ItemHolder, newItem: ItemHolder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemHolder, newItem: ItemHolder): Boolean {
        return oldItem == newItem
    }

}
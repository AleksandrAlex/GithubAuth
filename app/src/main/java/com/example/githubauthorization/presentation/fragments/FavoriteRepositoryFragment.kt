package com.example.githubauthorization.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubauthorization.R
import com.example.githubauthorization.adapter.AdapterFavoriteRepository
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.databinding.FragmentFavoriteRepositoriesBinding
import com.example.githubauthorization.db.EntityRepo
import com.example.githubauthorization.presentation.App
import com.example.githubauthorization.presentation.FavoriteRepositoryViewModel
import com.example.githubauthorization.presentation.FavoriteRepositoryViewModelFactory
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FavoriteRepositoryFragment : Fragment(R.layout.fragment_favorite_repositories){

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var favoriteRepositoryViewModelFactory: FavoriteRepositoryViewModelFactory

    private val favoriteRepositoryViewModel by viewModels<FavoriteRepositoryViewModel> { favoriteRepositoryViewModelFactory }

    lateinit var binding: FragmentFavoriteRepositoriesBinding

    private val favoriteAdapterRepository = AdapterFavoriteRepository({item -> onClick(item)}, {star -> starClick(star)})

    private fun starClick(item: EntityRepo) {
        favoriteRepositoryViewModel.removeRepositoryFromDB(item)

    }

    private fun onClick(item: EntityRepo) {
        findNavController()
            .navigate(
                FavoriteRepositoryFragmentDirections.actionFavoriteRepositoryFragmentToFavoriteDetailsRepository(
                    item.id
                )
            )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteRepositoriesBinding.inflate(layoutInflater)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        val itemList: RecyclerView = binding.recyclerListRepository
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = favoriteAdapterRepository
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenStarted {
            favoriteRepositoryViewModel.favoriteRepositories.collect {
                favoriteAdapterRepository.submitList(it)
            }
        }
    }
}
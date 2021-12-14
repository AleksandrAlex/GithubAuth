package com.example.githubauthorization.presentation.fragments

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubauthorization.NetworkConnection
import com.example.githubauthorization.NetworkUtil
import com.example.githubauthorization.Repository
import com.example.githubauthorization.adapter.AdapterRepository
import com.example.githubauthorization.adapter.RepositoryLoadStateAdapter
import com.example.githubauthorization.databinding.FragmentRepositoriesSearchBinding
import com.example.githubauthorization.getQueryTextChangeStateFlow
import com.example.githubauthorization.models.ItemHolder
import com.example.githubauthorization.presentation.App
import com.example.githubauthorization.presentation.SearchRepositoryViewModel
import com.example.githubauthorization.presentation.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class SearchRepositoryFragment: Fragment() {

    lateinit var binding: FragmentRepositoriesSearchBinding

    private val adapterRepository = AdapterRepository({ item -> onClick(item) }, { star ->
        onStarClick(
            star
        )
    })


    @Inject
    lateinit var networkUtil: NetworkUtil


    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val searchRepositoryViewModel by viewModels<SearchRepositoryViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesSearchBinding.inflate(layoutInflater)
        setupAdapter()
        return binding.root
    }

    @Suppress("NAME_SHADOWING")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()

        val receiver = NetworkConnection()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        this.requireContext().registerReceiver(receiver, intentFilter)

        receiver.hasConnect.observe(viewLifecycleOwner, {
            if (it){
                binding.nameRepository.visibility = View.VISIBLE
                binding.recyclerListRepository.visibility = View.VISIBLE
                lifecycleScope.launch {
                binding.nameRepository.getQueryTextChangeStateFlow()
                    .debounce(1000)
                    .filter {
                        return@filter !it.isEmpty()
                    }
                    .collect {
                        searchRepositoryViewModel.getRepositories(it)
                    }
            }
        }
        else{
            binding.nameRepository.setQuery("",false)
            binding.nameRepository.visibility = View.INVISIBLE
                binding.recyclerListRepository.visibility = View.INVISIBLE
            val snack: Snackbar = Snackbar.make(view,"No Internet Connection", Snackbar.LENGTH_LONG)
            val view = snack.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            snack.show()
        }
        })

    }

    private fun observeState() {
        searchRepositoryViewModel.repositories.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenStarted {
                adapterRepository.submitData(it)
            }
        })

        adapterRepository.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading){
                showProgress()
            }
            else{
                hideProgress()
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    else -> null
                }
                error?.let {
                    Exception(it.toString())
                }
            }



//            binding.apply {
//                loader.isVisible = loadState.refresh is LoadState.Loading
//                recyclerListRepository.isVisible = loadState.refresh is LoadState.NotLoading
//
//                if (loadState.refresh is LoadState.NotLoading &&
//                        loadState.append.endOfPaginationReached &&
//                        adapterRepository.itemCount < 1){
//                    recyclerListRepository.isVisible = false
//                }
//
//            }
        }
    }

    private fun setupAdapter() {
        val itemList: RecyclerView = binding.recyclerListRepository
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = adapterRepository.withLoadStateFooter(
            footer = RepositoryLoadStateAdapter { adapterRepository.retry() }

        )
    }

    private fun onClick(item: ItemHolder) {
        findNavController()
            .navigate(
                SearchRepositoryFragmentDirections
                    .actionSearchRepositoryFragmentToDetailRepositoryFragment(
                    item
                )
            )
    }

    private fun onStarClick(itemHolder: ItemHolder) {

        if (!itemHolder.isFavorite){
            searchRepositoryViewModel.saveRepository(itemHolder)
            Toast.makeText(context, "Repository was saved to favorites", Toast.LENGTH_LONG).show()
        }
        else {
            searchRepositoryViewModel.removeFromFavorites(itemHolder)
            Toast.makeText(context, "Repository was removed from favorites", Toast.LENGTH_LONG).show()
        }
//        searchRepositoryViewModel.getRepositories(binding.nameRepository.query.toString())
//        adapterRepository.notifyDataSetChanged()

    }

    private fun hideProgress() {
        binding.loader.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        binding.loader.visibility = View.VISIBLE
    }
}
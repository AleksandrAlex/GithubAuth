package com.example.githubauthorization.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubauthorization.NetworkUtil
import com.example.githubauthorization.adapter.AdapterRepository
import com.example.githubauthorization.adapter.RepositoryLoadStateAdapter
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.databinding.FragmentRepositoriesSearchBinding
import com.example.githubauthorization.models.Item
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class SearchRepositoryFragment: Fragment() {

    lateinit var binding: FragmentRepositoriesSearchBinding

    private val adapterRepository = AdapterRepository{ item -> onClick(item) }

    @Inject
    lateinit var networkUtil: NetworkUtil

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var searchRepositoryModelFactory: SearchRepositoryViewModelFactory

    private val searchRepositoryViewModel by viewModels<SearchRepositoryViewModel> { searchRepositoryModelFactory }


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        binding.searchBtn.setOnClickListener {
            if (networkUtil.isNetworkConnectedOrConnecting(this.requireContext())){
                val repoName = binding.nameRepository.text.toString()
                if (repoName.isNotEmpty()){
                    searchRepositoryViewModel.getRepositories(repoName)
                }
            }
            else{
                Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observeState() {
        searchRepositoryViewModel.repositories.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenResumed {
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
                    showError(it.error.message.toString())
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
        itemList.adapter = adapterRepository.withLoadStateHeaderAndFooter(
                footer = RepositoryLoadStateAdapter { adapterRepository.retry() },
                header = RepositoryLoadStateAdapter { adapterRepository.retry() }

        )
    }

    private fun onClick(item: Item) {
        findNavController()
            .navigate(
                    SearchRepositoryFragmentDirections
                            .actionSearchRepositoryFragmentToDetailRepositoryFragment(item)
            )
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
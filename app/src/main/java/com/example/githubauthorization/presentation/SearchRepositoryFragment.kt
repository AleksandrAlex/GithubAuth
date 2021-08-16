package com.example.githubauthorization.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubauthorization.adapter.AdapterRepository
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.databinding.FragmentRepositoriesSearchBinding
import com.example.githubauthorization.models.Item
import com.example.githubauthorization.models.ResponseRepositories
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepositoryFragment: Fragment() {

    lateinit var binding: FragmentRepositoriesSearchBinding

    private val adapterRepository = AdapterRepository{ item -> onClick(item) }

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
            val repoName = binding.nameRepository.text.toString()
            if (repoName.isNotEmpty()){
                searchRepositoryViewModel.getRepositories(repoName)
            }
        }
    }

    private fun observeState() {

        lifecycleScope.launchWhenStarted {
            searchRepositoryViewModel.state.collect {
                when(it){
                    is UiState.Empty -> Toast.makeText(context, "Empty", Toast.LENGTH_LONG).show()
                    is UiState.Error -> showError(it.errorMessage)
                    is UiState.Loading -> showProgress()
                    is UiState.Success ->{
                        adapterRepository.submitData(it.data)
                        hideProgress()
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        val itemList: RecyclerView = binding.recyclerListRepository
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = adapterRepository
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
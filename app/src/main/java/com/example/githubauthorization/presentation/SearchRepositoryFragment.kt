package com.example.githubauthorization.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.databinding.FragmentRepositoriesSearchBinding
import javax.inject.Inject

class SearchRepositoryFragment: Fragment() {

    lateinit var binding: FragmentRepositoriesSearchBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            val repoName = binding.nameRepository.text.toString()
            if (repoName.isNotEmpty()){
                searchRepositoryViewModel.getRepositories(repoName)
                searchRepositoryViewModel.state.observe(viewLifecycleOwner, Observer { state ->
                    when (state) {
                        is SearchRepositoryViewModelState.Error -> showError(state.errorMessage)
                        is SearchRepositoryViewModelState.Loading -> showProgress()
                        is SearchRepositoryViewModelState.Success ->{
                            hideProgress()
                            Log.d("REPOSITORY", state.result.toString())
                        }
                    }
                })
            }
        }
    }

    private fun hideProgress() {
        binding.loader.visibility = View.INVISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgress() {
        binding.loader.visibility = View.VISIBLE
    }
}
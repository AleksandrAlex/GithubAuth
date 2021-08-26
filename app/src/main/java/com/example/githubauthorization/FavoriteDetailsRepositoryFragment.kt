package com.example.githubauthorization

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
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubauthorization.databinding.FragmentFavoriteDetailsRepositoryBinding
import com.example.githubauthorization.presentation.App
import com.example.githubauthorization.presentation.FavoriteDetailsRepositoryViewModel
import com.example.githubauthorization.presentation.FavoriteDetailsRepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail_repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteDetailsRepositoryFragment : Fragment() {

    lateinit var binding: FragmentFavoriteDetailsRepositoryBinding

    private val args: FavoriteDetailsRepositoryFragmentArgs by navArgs()

    @Inject
    lateinit var factory: FavoriteDetailsRepositoryViewModelFactory

    private val favoriteDetailsRepositoryViewModel by viewModels<FavoriteDetailsRepositoryViewModel> { factory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteDetailsRepositoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = args.repoId

        favoriteDetailsRepositoryViewModel.setItem(itemId)

        lifecycleScope.launchWhenStarted {
            favoriteDetailsRepositoryViewModel.entityRepo.collect {
                binding.apply {
                    title.text = it.name
                    description.text = it.description
                    owner.text = it.login
                    image.load(it.avatar_url)
                }
            }
        }

        binding.bin.setOnClickListener {
            findNavController().navigate(R.id.favoriteRepositoryFragment)
            lifecycleScope.launch {
                favoriteDetailsRepositoryViewModel.removeRepositoryFromDatabase(itemId)
            }
            Toast.makeText(context, "Repository was removed!", Toast.LENGTH_LONG).show()
        }
    }
}
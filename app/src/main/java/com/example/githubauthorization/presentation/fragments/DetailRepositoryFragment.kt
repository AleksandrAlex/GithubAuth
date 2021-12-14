package com.example.githubauthorization.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubauthorization.R
import com.example.githubauthorization.Repository
import com.example.githubauthorization.databinding.FragmentDetailRepositoryBinding
import com.example.githubauthorization.presentation.App
import com.example.githubauthorization.presentation.DetailRepositoryFragmentViewModel
import com.example.githubauthorization.presentation.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail_repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailRepositoryFragment : Fragment() {

    @Inject
    lateinit var userRepository: Repository

    @Inject
    lateinit var factory: ViewModelFactory

    private val detailRepositoryFragmentViewModel by viewModels<DetailRepositoryFragmentViewModel> { factory }

    private val args: DetailRepositoryFragmentArgs by navArgs()

    lateinit var binding: FragmentDetailRepositoryBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailRepositoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = args.itemHolder
        binding.apply {
            description.text = item.item.description
            title.text = item.item.name
            owner.text = item.item.owner.login
            image.load(item.item.owner.avatar_url)
            if(item.isFavorite)
            star.setImageResource(R.drawable.ic_gold_star)
            else
                star.setImageResource(R.drawable.ic_star)
        }

        binding.star.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                detailRepositoryFragmentViewModel.saveRepository(item)
            }
            star.setImageResource(R.drawable.ic_gold_star)
            Toast.makeText(context, "Repository was added to favorites!", Toast.LENGTH_LONG).show()
        }

        binding.bin.setOnClickListener {
            lifecycleScope.launch {
                detailRepositoryFragmentViewModel.removeRepository(item)
            }
            star.setImageResource(R.drawable.ic_star)
            Toast.makeText(context, "Repository was removed!", Toast.LENGTH_LONG).show()
        }
    }
}
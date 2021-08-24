package com.example.githubauthorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubauthorization.data.UserRepository
import com.example.githubauthorization.databinding.FragmentDetailRepositoryBinding
import com.example.githubauthorization.presentation.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailRepositoryFragment : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository

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

        val item = args.item
        binding.apply {
            description.text = item.description
            title.text = item.name
            owner.text = item.owner.login
            image.load(item.owner.avatar_url)
        }

        binding.star.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                userRepository.saveRepository(item)
            }
            Toast.makeText(context, "Repository was added to favorites!", Toast.LENGTH_LONG).show()
        }
    }
}
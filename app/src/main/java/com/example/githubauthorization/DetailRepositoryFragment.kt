package com.example.githubauthorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.githubauthorization.databinding.FragmentDetailRepositoryBinding


class DetailRepositoryFragment : Fragment() {

    private val args: DetailRepositoryFragmentArgs by navArgs()

    lateinit var binding: FragmentDetailRepositoryBinding

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
    }
}
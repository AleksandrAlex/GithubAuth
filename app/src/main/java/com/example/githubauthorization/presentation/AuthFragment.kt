package com.example.githubauthorization.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.R
import com.example.githubauthorization.UserRepository
import com.example.githubauthorization.databinding.FragmentAuthBinding
import com.example.githubauthorization.domain.UserProfileViewModel
import com.example.githubauthorization.domain.UserProfileViewModelState
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.confirmBtn.setOnClickListener {
            val userName = binding.name.text.toString()
            if (userName.isEmpty()){
                Toast.makeText(this.context, "Please enter a user name!", Toast.LENGTH_LONG).show()
            }
            else{
                findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToProfileFragment(userName))
            }


        }
    }

}
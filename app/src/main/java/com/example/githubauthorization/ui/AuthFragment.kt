package com.example.githubauthorization.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.R
import com.example.githubauthorization.databinding.FragmentAuthBinding
import kotlinx.coroutines.launch
import okhttp3.Credentials
import javax.inject.Inject


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

@Inject
lateinit var api: GitHubApi

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

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
            val name = binding.name.text.toString()
            val password = binding.pass.text.toString()

            val token = createToken(name, password)

            lifecycleScope.launch {
            val repo = api.getRepo(token)
                Log.d("MYTAG", repo.size.toString())
            }

        }
    }

    private fun createToken(name: String, password: String): String {
        return Credentials.basic(name, password)
    }

}
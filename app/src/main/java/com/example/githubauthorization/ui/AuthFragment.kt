package com.example.githubauthorization.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.githubauthorization.GitHubApi
import com.example.githubauthorization.R
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthFragment : Fragment(R.layout.fragment_auth) {


@Inject
lateinit var api: GitHubApi

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val user = api.getUser()
            if (user.isSuccessful){
                Log.d("MYTAG", user.body()!!.login)
            }

        }

    }

}
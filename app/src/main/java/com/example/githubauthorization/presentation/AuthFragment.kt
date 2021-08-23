package com.example.githubauthorization.presentation

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.githubauthorization.NetworkUtil
import com.example.githubauthorization.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    @Inject
    lateinit var networkUtil: NetworkUtil


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

    @Suppress("NAME_SHADOWING")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmBtn.setOnClickListener {
            if (networkUtil.isNetworkConnected(this.requireContext())){
                val userName = binding.name.text.toString()
                if (userName.isNotEmpty()){
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToProfileFragment(userName))
                }
            }
            else{
                val snack: Snackbar = Snackbar.make(view,"No Internet Connection", Snackbar.LENGTH_LONG)
                val view = snack.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                view.layoutParams = params
                snack.show()
            }
        }
    }
}
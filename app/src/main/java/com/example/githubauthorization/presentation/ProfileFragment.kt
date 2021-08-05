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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubauthorization.R
import com.example.githubauthorization.UserRepository
import com.example.githubauthorization.databinding.FragmentProfileBinding
import com.example.githubauthorization.domain.UserProfileViewModel
import com.example.githubauthorization.domain.UserProfileViewModelState
import com.example.githubauthorization.model.UserProfile
import retrofit2.Response
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs? by navArgs()

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var userProfileViewModelFactory: UserProfileViewModelFactory

    private val userViewModel by viewModels<UserProfileViewModel> { userProfileViewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = args?.userName
        userName?.let { Log.d("UserName", it) }

        userName?.let { userViewModel.getUserProfile(it) }
        userViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is UserProfileViewModelState.Error -> {
                    findNavController().navigate(R.id.authFragment)
                    showError(state.errorMessage)

                }
                is UserProfileViewModelState.Loading -> showProgressBar()
                is UserProfileViewModelState.Success -> {
                    hideLoader()
                    state.userProfile?.let { setData(it) }
                }
            }
        })
    }

    private fun hideLoader() {
        binding.loader.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        hideLoader()
        Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun setData(userProfile: UserProfile) {
        binding.login.text = userProfile.login
        binding.name.text = userProfile.name
        binding.userImage.load(userProfile.avatar_url){
            placeholder(R.drawable.ic_account)
            transformations(CircleCropTransformation())
        }
    }

}
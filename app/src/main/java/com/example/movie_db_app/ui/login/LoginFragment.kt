package com.example.movie_db_app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.databinding.FragmentLoginBinding
import com.example.movie_db_app.ui.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
    }


    private fun registerListeners() {
        binding.registerHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        // TODO 1. hash password
        //      2. replace doesUserExist with get user and save the currently logged user in SharedPreferences
        //      3. Display Error message based on what's wrong
        //              case 1 -> user doesn't exist
        //              case 2 -> password is wrong

        if (email.isNotEmpty() && password.isNotEmpty()) {
            userViewModel.doesUserExist(email).observe(viewLifecycleOwner, Observer {
                if (it > 0) {
                    findNavController().navigate(R.id.action_loginFragment_to_trendingFragment)
                }
            })
        }
    }

    // TODO Add user to shared preferences(already done something in viewModel
    //      add log for checking shared preferences


}



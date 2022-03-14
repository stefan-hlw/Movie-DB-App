package com.example.movie_db_app.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.ui.UserViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerListeners()
    }


    private fun registerListeners() {
        view?.findViewById<TextView>(R.id.register_here)?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        view?.findViewById<Button>(R.id.login_button)?.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = view?.findViewById<EditText>(R.id.email_input)?.text.toString().trim()
        val password = view?.findViewById<EditText>(R.id.password_input)?.text.toString().trim()
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



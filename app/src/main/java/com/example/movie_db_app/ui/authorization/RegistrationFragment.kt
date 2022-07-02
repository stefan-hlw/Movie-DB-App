package com.example.movie_db_app.ui.authorization

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.database.sqlite.SQLiteConstraintException
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RegistrationFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        binding.loginHere.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {
            createUser()
        }

        val minCalendar = Calendar.getInstance()
        val maxCalendar = Calendar.getInstance()

        minCalendar.time = Date(minCalendar.timeInMillis)
        minCalendar.add(Calendar.YEAR, -8)

        maxCalendar.time = Date(maxCalendar.timeInMillis)
        maxCalendar.add(Calendar.YEAR, -80)

        Log.i(maxCalendar.time.toString(), "maxCalendar")
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        val maxDate = minCalendar.time
        val minDate = maxCalendar.time

        binding.birthdayPicker.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).run {
                minDate?.time?.also { datePicker.minDate = it }
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    private fun createUser() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        val fullName = binding.fullNameInput.text.toString().trim()
        val birthday = binding.birthdayPicker.text.toString()
        val user = User(email, password, fullName, birthday)
        userViewModel.createUser(user)
        showRegistrationPopUpDialog()
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.birthdayPicker.text = sdf.format(cal)
    }

    private fun showRegistrationPopUpDialog() {
        val dialogBuilder = AlertDialog.Builder(context!!)
        dialogBuilder.setMessage("The user has been successfully created, you will be redirected to login page")
        dialogBuilder.setPositiveButton("Ok",
            DialogInterface.OnClickListener { dialog, whichButton ->
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            })
        val b = dialogBuilder.create()
        b.show()
    }

}


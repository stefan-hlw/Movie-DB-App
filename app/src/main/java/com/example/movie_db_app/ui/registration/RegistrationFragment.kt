package com.example.movie_db_app.ui.registration

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.ui.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*

class RegistrationFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()

    val cal = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerListeners()
    }

    private fun registerListeners() {
        view?.findViewById<TextView>(R.id.login_here)?.setOnClickListener{
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        view?.findViewById<Button>(R.id.register_button)?.setOnClickListener{
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

        view?.findViewById<TextView>(R.id.birthday_picker)?.setOnClickListener {
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
        val email = view?.findViewById<EditText>(R.id.email_input)?.text.toString().trim()
        val password = view?.findViewById<EditText>(R.id.password_input)?.text.toString().trim()
        val fullName = view?.findViewById<EditText>(R.id.full_name_input)?.text.toString().trim()
        val birthday = view?.findViewById<TextView>(R.id.birthday_picker)?.text.toString()

        userViewModel.createUser(email, password, fullName, birthday)
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        view?.findViewById<TextView>(R.id.birthday_picker)?.text = sdf.format(cal)
    }


}


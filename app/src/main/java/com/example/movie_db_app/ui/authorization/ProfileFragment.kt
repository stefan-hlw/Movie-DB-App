package com.example.movie_db_app.ui.authorization

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.User
import com.example.movie_db_app.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var uneditedUser: User

    private val cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        userViewModel.getUserProfile().observe(viewLifecycleOwner, Observer {
            with(binding) {
                uneditedUser = User(it!!.email, it.password, it.full_name, it.birthday)
                emailInput.setText(it.email)
                passwordInput.setText(it.password)
                fullNameInput.setText(it.full_name)
                birthdayText.text = it.birthday
            }
        })
    }

    private fun setListeners() {
        binding.editProfileButton.setOnClickListener {
            editUser()
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

    private fun editUser() {
        with(binding) {
            val birthday: String = if (birthdayPicker.text.toString() == getString(R.string.birthday_picker)) {
                uneditedUser.birthday.toString()
            } else {
                birthdayPicker.text.toString()
            }
            val editedUser = User(emailInput.text.toString(), passwordInput.text.toString(), fullNameInput.text.toString(), birthday)
            userViewModel.editUser(editedUser)
        }

    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.birthdayPicker.text = sdf.format(cal)
    }

}
package com.example.movie_db_app.ui.home

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.*

abstract class BaseHomeFragment : Fragment() {

    private val REQUEST_CODE_SPEECH_INPUT = 1
    protected lateinit var searchView : SearchView

    protected fun setMic(mic : ImageView) {
        mic.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Log.d(e.toString(), "Speech to text error")
            }
        }
    }


    fun asd() {

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == Activity.RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                searchView.setQuery(
                    Objects.requireNonNull(res)[0],
                    false
                )
            }
        }
    }

    protected fun setSearchBar(searchView: SearchView, mic : ImageView, title : TextView, destination: Int) {
        searchView.isIconified = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query, destination)
                searchView.isIconified = true
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                title.visibility = View.VISIBLE
                mic.visibility = View.GONE
                return false
            }
        })
        searchView.setOnSearchClickListener {
            title.visibility = View.GONE
            mic.visibility = View.VISIBLE
        }
    }

    private fun performSearch(category: String?, destination : Int) {
        val bundle = bundleOf("category" to category)
        findNavController().navigate(destination, bundle)
    }
}
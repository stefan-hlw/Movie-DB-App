package com.example.movie_db_app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.movie_db_app.R
import com.example.movie_db_app.data.database.GenresDbModel
import com.example.movie_db_app.databinding.FragmentGenresBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresFragment : Fragment(), GenresAdapter.OnItemClickListener {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!
    private var genreAdapter: GenresAdapter? = null
    private val genresViewModel by viewModel<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableBackButton()
        setActionBarTop()
        setObservers()
        getData()
    }

    private fun getData() {
        genresViewModel.getGenresFromDb()
    }

    private fun setObservers() {
        genresViewModel.genresDbData.observe(viewLifecycleOwner, Observer {
            setGenreListAdapter(it)
            genreAdapter?.notifyDataSetChanged()
        })
    }

    private fun setActionBarTop() {
        binding.actionBarGenres.actionBarTopText.text = getString(R.string.genres)
        binding.actionBarGenres.profileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_genresFragment_to_editProfileFragment)
        }

        val searchView : SearchView = binding.actionBarGenres.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                openCategory(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

    }

    override fun openCategory(category: String?) {
        val bundle = bundleOf("category" to category)
        findNavController().navigate(R.id.action_genresFragment_to_genreResultsFragment, bundle)
    }

    private fun setGenreListAdapter(genreList: List<GenresDbModel>) {
        genreAdapter = GenresAdapter(genreList)
        genreAdapter?.setOnItemClickListener(this)
        binding.rcGenresList.adapter = genreAdapter
    }

    private fun disableBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // this disables the Android native back button
                }
            }
        )
    }
}
package com.makinul.paging3.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.makinul.paging3.data.network.MovieApiService
import com.makinul.paging3.data.network.MovieRepository
import com.makinul.paging3.databinding.FragmentMoviesBinding
import com.makinul.paging3.ui.movies.MoviePagerAdapter
import com.makinul.paging3.ui.movies.MovieViewModelFactory
import com.makinul.paging3.ui.movies.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var viewModel: MoviesViewModel
    private val adapter = MoviePagerAdapter()
    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = MovieApiService.getInstance()
        val repository = MovieRepository(apiService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MoviesViewModel::class.java]

        viewModel.errorMessage.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            viewModel.getMovieList().observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
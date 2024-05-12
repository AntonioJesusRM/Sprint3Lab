package com.example.sprint3lab.ui.movie

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sprint3lab.R
import com.example.sprint3lab.data.Constant
import com.example.sprint3lab.data.model.GenresModel
import com.example.sprint3lab.databinding.ActivityDetailsMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMovieBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var detailsAdapter: DetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idMovie = intent.getStringExtra("idMovie")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        detailsViewModel.getDetailsMovie(idMovie)
        observerViewModel()
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            detailsViewModel.loadingSharedFlow.collect { isLoading ->
                showLoading(isLoading)
            }
        }
        lifecycleScope.launch {
            detailsViewModel.moviesDetails.collect { dataSet ->
                Glide
                    .with(this@DetailsMovieActivity)
                    .load("${Constant.URL_IMAGE}${dataSet?.poster}")
                    .apply(
                        RequestOptions().override(
                            Constant.IMG_WIDTH_DETAILS,
                            Constant.IMG_HEIGHT_DETAILS
                        )
                    )
                    .into(binding.ivPosterDetails)
                binding.tvTitle.text = dataSet?.title
                binding.tvOverview.text = dataSet?.overview
                setupRecyclerView(dataSet?.genres)
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean)
            binding.pbLoadingDetails.visibility = View.VISIBLE
        else
            binding.pbLoadingDetails.visibility = View.GONE
    }

    private fun setupRecyclerView(listGenres: List<GenresModel>?) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvListGenres.layoutManager = layoutManager
        detailsAdapter = DetailsAdapter(listGenres)
        binding.rvListGenres.adapter = detailsAdapter
    }
}
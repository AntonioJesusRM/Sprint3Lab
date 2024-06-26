package com.example.sprint3lab.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint3lab.R
import com.example.sprint3lab.databinding.ActivityMainBinding
import com.example.sprint3lab.ui.movie.DetailsMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapterMain: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startActivity(intent)
        setupRecyclerView()
        observerViewModel()
        scrollRecyclerView()
        mainViewModel.getListMovies()
    }

    private fun scrollRecyclerView() {
        binding.rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE)
                    mainViewModel.getNextPage()
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE)
                    mainViewModel.getLastPage()
            }
        })
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            mainViewModel.loadingSharedFlow.collect { isLoading ->
                showLoading(isLoading)
            }
        }
        lifecycleScope.launch {
            mainViewModel.moviesList.collect { dataSet ->
                adapterMain.updateData(dataSet)
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean)
            binding.pbLoading.visibility = View.VISIBLE
        else
            binding.pbLoading.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 3)
        binding.rvListMovies.layoutManager = layoutManager
        adapterMain = MainAdapter(this, arrayListOf()) { id ->
            val intent = Intent(this, DetailsMovieActivity::class.java)
            intent.putExtra("idMovie", id)
            startActivity(intent)
        }
        binding.rvListMovies.adapter = adapterMain
    }

}
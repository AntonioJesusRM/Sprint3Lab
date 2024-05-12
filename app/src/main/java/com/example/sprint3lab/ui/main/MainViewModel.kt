package com.example.sprint3lab.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint3lab.data.model.MoviesModel
import com.example.sprint3lab.data.useCase.GetListMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getListMoviesUseCase: GetListMoviesUseCase
) : ViewModel() {
    private var page = 1
    private var moviesListMutable = MutableStateFlow<List<MoviesModel>>(arrayListOf())
    val moviesList: StateFlow<List<MoviesModel>> = moviesListMutable
    private var loadingMutableSharedFlow = MutableStateFlow(false)
    val loadingSharedFlow: StateFlow<Boolean> = loadingMutableSharedFlow

    fun getListMovies(dispatcherIO: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcherIO) {
            loadingMutableSharedFlow.emit(true)
            try {
                delay(500)
                val response = getListMoviesUseCase(page).execute()
                if (response.isSuccessful) {
                    loadingMutableSharedFlow.emit(false)
                    moviesListMutable.value = response.body()?.result ?: emptyList()
                } else {
                    Log.d("%>", "Error: ${response.code()}")
                    loadingMutableSharedFlow.emit(false)
                }
            } catch (e: Exception) {
                Log.d("%>", "Error: ${e.message}")
                loadingMutableSharedFlow.emit(false)
            }
        }
    }

    fun getLastPage() {
        if (page > 1) {
            page--
            getListMovies()
        }
    }

    fun getNextPage() {
        page++
        getListMovies()
    }

}
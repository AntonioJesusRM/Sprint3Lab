package com.example.sprint3lab.ui.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint3lab.data.model.DetailsModel
import com.example.sprint3lab.data.useCase.GetDetailsMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsMovieUseCase: GetDetailsMovieUseCase
) : ViewModel() {
    private var moviesDetailsMutable = MutableStateFlow<DetailsModel?>(null)
    val moviesDetails: StateFlow<DetailsModel?> = moviesDetailsMutable
    private var loadingMutableSharedFlow = MutableStateFlow(false)
    val loadingSharedFlow: StateFlow<Boolean> = loadingMutableSharedFlow
    fun getDetailsMovie(idMovie: String?, dispatcherIO: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcherIO) {
            loadingMutableSharedFlow.emit(true)
            try {
                val id = idMovie?.toInt()
                val response = getDetailsMovieUseCase(id ?: 0).execute()
                if (response.isSuccessful) {
                    loadingMutableSharedFlow.emit(false)
                    moviesDetailsMutable.value = response.body()
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
}
package com.adonaipinheiro.android_uspmovies.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.usecases.GetMovieDetails
import com.adonaipinheiro.android_uspmovies.domain.usecases.ObserveIsFavorite
import com.adonaipinheiro.android_uspmovies.domain.usecases.ToggleFavorite
import androidx.navigation.toRoute
import com.adonaipinheiro.android_uspmovies.navigation.DetailRoute
import com.adonaipinheiro.android_uspmovies.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// camada: presentation — o ViewModel concentra a lógica; a View é burra.
@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetails: GetMovieDetails,
    private val toggleFavoriteUseCase: ToggleFavorite,
    private val observeIsFavoriteUseCase: ObserveIsFavorite
) : ViewModel() {

    private val movieId: Int = savedStateHandle.toRoute<DetailRoute>().movieId

    private val _state = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val state: StateFlow<UiState<Movie>> = _state.asStateFlow()

    val isFavorite: Flow<Boolean> = observeIsFavoriteUseCase(movieId)

    init {
        load()
    }

    fun retry() = load()

    fun toggleFavorite() {
        val current = _state.value
        if (current is UiState.Data) {
            viewModelScope.launch { toggleFavoriteUseCase(current.value) }
        }
    }

    private fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movie = getMovieDetails(movieId)
                _state.value = UiState.Data(movie)
            } catch (error: Exception) {
                _state.value = UiState.Error(error.message ?: "Erro desconhecido")
            }
        }
    }
}

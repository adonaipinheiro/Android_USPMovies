package com.adonaipinheiro.android_uspmovies.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.usecases.GetFavorites
import com.adonaipinheiro.android_uspmovies.domain.usecases.ObserveIsFavorite
import com.adonaipinheiro.android_uspmovies.domain.usecases.ToggleFavorite
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
class FavoritesViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val toggleFavoriteUseCase: ToggleFavorite,
    private val observeIsFavoriteUseCase: ObserveIsFavorite
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Movie>>> = _state.asStateFlow()

    fun onAppear() = reload()

    fun reload() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movies = getFavorites()
                _state.value = if (movies.isEmpty()) UiState.Empty else UiState.Data(movies)
            } catch (error: Exception) {
                _state.value = UiState.Error(error.message ?: "Erro desconhecido")
            }
        }
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = observeIsFavoriteUseCase(movieId)

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
            reload()
        }
    }
}

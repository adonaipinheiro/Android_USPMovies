package com.adonaipinheiro.android_uspmovies.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.usecases.GetPopularMovies
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
class PopularViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val toggleFavoriteUseCase: ToggleFavorite,
    private val observeIsFavoriteUseCase: ObserveIsFavorite
) : ViewModel() {

    private val movies = mutableListOf<Movie>()
    private var page = 1
    private var canLoadMore = true
    private var isLoadingPage = false

    private val _state = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Movie>>> = _state.asStateFlow()

    fun onAppear() {
        if (movies.isNotEmpty()) return
        loadFirstPage()
    }

    fun reload() {
        page = 1
        canLoadMore = true
        loadFirstPage()
    }

    fun loadNextPageIfNeeded(currentMovie: Movie) {
        if (!canLoadMore || isLoadingPage) return
        val nearEnd = movies.takeLast(3).any { it.id == currentMovie.id }
        if (nearEnd) loadNextPage()
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = observeIsFavoriteUseCase(movieId)

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch { toggleFavoriteUseCase(movie) }
    }

    private fun loadFirstPage() {
        isLoadingPage = true
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = getPopularMovies(page = 1)
                movies.clear()
                movies.addAll(result)
                page = 1
                _state.value = if (movies.isEmpty()) UiState.Empty else UiState.Data(movies.toList())
            } catch (error: Exception) {
                _state.value = UiState.Error(error.message ?: "Erro desconhecido")
            }
            isLoadingPage = false
        }
    }

    private fun loadNextPage() {
        isLoadingPage = true
        viewModelScope.launch {
            try {
                val result = getPopularMovies(page = page + 1)
                if (result.isEmpty()) {
                    canLoadMore = false
                } else {
                    page += 1
                    movies.addAll(result)
                    _state.value = UiState.Data(movies.toList())
                }
            } catch (error: Exception) {
                canLoadMore = false
            }
            isLoadingPage = false
        }
    }
}

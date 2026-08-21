package com.adonaipinheiro.android_uspmovies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adonaipinheiro.android_uspmovies.domain.entities.Movie
import com.adonaipinheiro.android_uspmovies.domain.usecases.ObserveIsFavorite
import com.adonaipinheiro.android_uspmovies.domain.usecases.SearchMovies
import com.adonaipinheiro.android_uspmovies.domain.usecases.ToggleFavorite
import com.adonaipinheiro.android_uspmovies.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// camada: presentation — o ViewModel concentra a lógica; a View é burra.
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: SearchMovies,
    private val toggleFavoriteUseCase: ToggleFavorite,
    private val observeIsFavoriteUseCase: ObserveIsFavorite
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow<UiState<List<Movie>>>(UiState.Empty)
    val state: StateFlow<UiState<List<Movie>>> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(newValue: String) {
        _query.value = newValue
        searchJob?.cancel()

        val trimmed = newValue.trim()
        if (trimmed.isEmpty()) {
            _state.value = UiState.Empty
            return
        }

        searchJob = viewModelScope.launch {
            delay(400)
            performSearch(trimmed)
        }
    }

    fun retry() {
        val trimmed = _query.value.trim()
        if (trimmed.isEmpty()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch { performSearch(trimmed) }
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = observeIsFavoriteUseCase(movieId)

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch { toggleFavoriteUseCase(movie) }
    }

    private suspend fun performSearch(query: String) {
        _state.value = UiState.Loading
        try {
            val movies = searchMovies(query = query, page = 1)
            _state.value = if (movies.isEmpty()) UiState.Empty else UiState.Data(movies)
        } catch (cancellation: CancellationException) {
            // Uma busca mais recente cancelou esta — não é um erro para o usuário ver.
            throw cancellation
        } catch (error: Exception) {
            _state.value = UiState.Error(error.message ?: "Erro desconhecido")
        }
    }
}

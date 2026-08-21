package com.adonaipinheiro.android_uspmovies.presentation.state

// camada: presentation — estados de UI explícitos (loading/data/empty/error).
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Data<T>(val value: T) : UiState<T>
    data object Empty : UiState<Nothing>
    data class Error(val message: String) : UiState<Nothing>
}

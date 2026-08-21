package com.adonaipinheiro.android_uspmovies.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adonaipinheiro.android_uspmovies.presentation.components.AppBackground
import com.adonaipinheiro.android_uspmovies.presentation.components.MovieCard
import com.adonaipinheiro.android_uspmovies.presentation.components.StateContent

// camada: presentation — View burra: consome estado pronto do ViewModel.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onAppear() }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        AppBackground()
        StateContent(
            state = state,
            onRetry = viewModel::reload,
            modifier = Modifier
                .fillMaxSize()
        ) { movies ->
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = padding.calculateTopPadding() + 8.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movies, key = { it.id }) { movie ->
                    val isFavorite by remember(movie.id) { viewModel.isFavorite(movie.id) }
                        .collectAsStateWithLifecycle(initialValue = true)

                    MovieCard(
                        movie = movie,
                        isFavorite = isFavorite,
                        onToggleFavorite = { viewModel.toggleFavorite(movie) },
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

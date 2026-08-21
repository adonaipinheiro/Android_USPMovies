package com.adonaipinheiro.android_uspmovies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

// camada: presentation — navegação desacoplada da tela: a tela chama
// coordinator.goToDetail(id) sem conhecer o NavHostController por baixo.
class AppCoordinator(private val navController: NavHostController) {
    fun goToDetail(movieId: Int) {
        navController.navigate(DetailRoute(movieId))
    }

    fun goBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberAppCoordinator(navController: NavHostController): AppCoordinator =
    remember(navController) { AppCoordinator(navController) }

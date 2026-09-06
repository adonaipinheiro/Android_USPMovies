package com.adonaipinheiro.android_uspmovies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

// camada: presentation (navegação) — desacoplada da tela: a tela chama
// coordinator.goToDetail(id) sem conhecer o NavHostController por baixo.
// Navegação vive dentro de presentation porque é sobre "qual tela mostrar",
// decisão de UI, não de domínio/dados.
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

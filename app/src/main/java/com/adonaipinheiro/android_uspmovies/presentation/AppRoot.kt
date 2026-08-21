package com.adonaipinheiro.android_uspmovies.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import com.adonaipinheiro.android_uspmovies.navigation.DetailRoute
import com.adonaipinheiro.android_uspmovies.navigation.FavoritesRoute
import com.adonaipinheiro.android_uspmovies.navigation.PopularRoute
import com.adonaipinheiro.android_uspmovies.navigation.SearchRoute
import com.adonaipinheiro.android_uspmovies.navigation.rememberAppCoordinator
import com.adonaipinheiro.android_uspmovies.presentation.detail.DetailScreen
import com.adonaipinheiro.android_uspmovies.presentation.favorites.FavoritesScreen
import com.adonaipinheiro.android_uspmovies.presentation.popular.PopularScreen
import com.adonaipinheiro.android_uspmovies.presentation.search.SearchScreen

// camada: presentation — a raiz que monta as abas e o NavHost.
@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val coordinator = rememberAppCoordinator(navController)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val isPopular = destination?.hasRoute<PopularRoute>() == true
    val isSearch = destination?.hasRoute<SearchRoute>() == true
    val isFavorites = destination?.hasRoute<FavoritesRoute>() == true
    val isTopLevel = isPopular || isSearch || isFavorites

    Scaffold(
        bottomBar = {
            if (isTopLevel) {
                NavigationBar {
                    NavigationBarItem(
                        selected = isPopular,
                        onClick = { navController.navigateToTab(PopularRoute) },
                        icon = { Icon(Icons.Filled.LocalFireDepartment, contentDescription = null) },
                        label = { Text("Populares") }
                    )
                    NavigationBarItem(
                        selected = isSearch,
                        onClick = { navController.navigateToTab(SearchRoute) },
                        icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        label = { Text("Buscar") }
                    )
                    NavigationBarItem(
                        selected = isFavorites,
                        onClick = { navController.navigateToTab(FavoritesRoute) },
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text("Favoritos") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = PopularRoute,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable<PopularRoute> { PopularScreen(onMovieClick = coordinator::goToDetail) }
            composable<SearchRoute> { SearchScreen(onMovieClick = coordinator::goToDetail) }
            composable<FavoritesRoute> { FavoritesScreen(onMovieClick = coordinator::goToDetail) }
            composable<DetailRoute> { DetailScreen(onBack = coordinator::goBack) }
        }
    }
}

private fun NavHostController.navigateToTab(route: Any) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

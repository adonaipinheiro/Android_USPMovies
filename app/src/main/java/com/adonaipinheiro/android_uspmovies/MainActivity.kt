package com.adonaipinheiro.android_uspmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adonaipinheiro.android_uspmovies.presentation.AppRoot
import com.adonaipinheiro.android_uspmovies.ui.theme.Android_USPMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_USPMoviesTheme {
                AppRoot()
            }
        }
    }
}

package com.example.starcharles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.starcharles.ui.theme.StarCharlesTheme
class MainActivity : ComponentActivity() {
    private val viewModel by lazy { MainViewModel() }
    val urlArgument = "urls"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarCharlesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val title = remember { mutableStateOf("StarCharles") }

                    DisposableEffect(navController) {
                        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                            when (destination.route) {
                                "main_screen" -> title.value = "StarCharles"
                                "characters_screen" -> title.value = "Characters"
                                "films_screen" -> title.value = "Films"
                                "films_screen/{$urlArgument}" -> title.value = "Films"
                                "characters_screen/{$urlArgument}" -> title.value = "Characters"
                            }
                        }
                        navController.addOnDestinationChangedListener(listener)
                        onDispose { navController.removeOnDestinationChangedListener(listener) }
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(title.value) }
                            )
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "main_screen"
                        ) {
                            composable("main_screen") {
                                MainScreen(navController = navController)
                            }
                            composable("characters_screen") {
                                CharactersScreen(
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                            composable("films_screen") {
                                FilmsScreen(
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                            composable(
                                route = "films_screen/{$urlArgument}",
                                arguments = listOf(navArgument(urlArgument) { type = NavType.StringType })
                            ) { backStackEntry ->
                                val urlListString = backStackEntry.arguments?.getString(urlArgument) ?: ""
                                val urlList = urlListString.split('|').filter { it.isNotEmpty() }
                                FilmsScreen(
                                    viewModel = viewModel,
                                    navController = navController,
                                    urls = urlList
                                )
                            }
                            composable(
                                route = "characters_screen/{$urlArgument}",
                                arguments = listOf(navArgument(urlArgument) { type = NavType.StringType })
                            ) { backStackEntry ->
                                val urlListString = backStackEntry.arguments?.getString(urlArgument) ?: ""
                                val urlList = urlListString.split('|').filter { it.isNotEmpty() }
                                CharactersScreen(
                                    viewModel = viewModel,
                                    navController = navController,
                                    urls = urlList
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

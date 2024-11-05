package com.example.notepad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notepad.presentation.screens.AddNotesScreen
import com.example.notepad.presentation.screens.NoteListScreen

enum class Screen {
    MainScreen,
    AddNotesScreen,
}

sealed class NavigationItem(val route: String) {
    object MainScreen : NavigationItem(Screen.MainScreen.name)
    object AddNotesScreen : NavigationItem(Screen.AddNotesScreen.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.MainScreen.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.MainScreen.route) {
            NoteListScreen(navController = navController)
        }
        composable(NavigationItem.AddNotesScreen.route) {
            AddNotesScreen(navController = navController)
        }
    }
}
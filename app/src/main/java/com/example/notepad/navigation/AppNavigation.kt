package com.example.notepad.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notepad.presentation.components.LoadingText
import com.example.notepad.presentation.screens.AddNotesScreen
import com.example.notepad.presentation.screens.DetailNoteScreen
import com.example.notepad.presentation.screens.NoteListScreen
import com.example.notepad.presentation.viewmodels.NotesViewModel

enum class Screen {
    MainScreen,
    AddNotesScreen,
}

sealed class NavigationItem(val route: String) {
    object MainScreen : NavigationItem(Screen.MainScreen.name)
    object AddNotesScreen : NavigationItem(Screen.AddNotesScreen.name)
    object NoteDetailScreen: NavigationItem("NoteDetailScreen/{Id}") {
        fun createRoute(Id: String) = "NoteDetailScreen/$Id"
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.MainScreen.route,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val searchQuery = remember { mutableStateOf("") }

    val onQueryChange: (String) -> Unit = { query ->
        searchQuery.value = query
    }

    val onClear: () -> Unit = {
        searchQuery.value = ""
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.MainScreen.route) {
            NoteListScreen(
                navController = navController,
                searchQuery = searchQuery.value,
                onQueryChange = onQueryChange,
                onClear = onClear,
                viewModel = viewModel
            )
        }

        composable(NavigationItem.AddNotesScreen.route) {
            AddNotesScreen(navController = navController)
        }

        composable(
            route = NavigationItem.NoteDetailScreen.route,
            arguments = listOf(navArgument("Id") { type = NavType.StringType })
        ) { backStackEntry ->
            val Id = backStackEntry.arguments?.getString("Id")
            Id?.let {
                LaunchedEffect(it) {
                    viewModel.getNoteById(it.toLong())
                }
                val selectedNote by viewModel.selectedFetchedNotes.collectAsState()
                if (selectedNote == null) {
                    LoadingText()
                } else {
                    DetailNoteScreen(noteEntity = selectedNote!!, navController = navController)
                }
            }
        }
    }
}

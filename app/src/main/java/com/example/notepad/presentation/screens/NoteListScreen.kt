package com.example.notepad.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepad.navigation.Screen
import com.example.notepad.presentation.components.MainTopBar
import com.example.notepad.presentation.components.NoNotesScreen
import com.example.notepad.presentation.components.NoteCardItem
import com.example.notepad.presentation.components.SearchNotesBar
import com.example.notepad.presentation.viewmodels.NotesViewModel

@Composable
fun NoteListScreen(
    navController: NavController,
    searchQuery: String = "",
    onQueryChange: (String) -> Unit = {},
    onClear: () -> Unit = {},
    viewModel: NotesViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.getAllNotes()
    }
    val fetchedNotes by viewModel.fetchedNotes.collectAsState()
    val loading by viewModel.loading.collectAsState()

    when {
        loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Loading...",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        fetchedNotes.isNullOrEmpty() -> {
            NoNotesScreen(onAddNoteClick = {
                navController.navigate(Screen.AddNotesScreen.name)
            }, navController = navController)
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    MainTopBar(navController = navController)
                    SearchNotesBar(
                        searchQuery = searchQuery,
                        onQueryChange = onQueryChange,
                        onClear = onClear,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(fetchedNotes)
                        { note ->
                            NoteCardItem(note)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp, 55.dp)
                        .size(56.dp)
                        .border(1.dp, Color.White, shape = FloatingActionButtonDefaults.largeShape)
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.AddNotesScreen.name) },
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Black,
                        shape = FloatingActionButtonDefaults.largeShape
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

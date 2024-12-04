package com.example.notepad.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepad.navigation.NavigationItem
import com.example.notepad.navigation.Screen
import com.example.notepad.presentation.components.LoadingText
import com.example.notepad.presentation.components.MainTopBar
import com.example.notepad.presentation.components.NoNotesScreen
import com.example.notepad.presentation.components.NoteCardItem
import com.example.notepad.presentation.components.SearchNotesBar
import com.example.notepad.presentation.viewmodels.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteListScreen(
    navController: NavController,
    searchQuery: String = "",
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val loading by viewModel.loading.collectAsState()
    val fetchedNotes by viewModel.fetchedNotes.collectAsState()

    val filteredNotes = fetchedNotes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.desc.contains(searchQuery, ignoreCase = true)
    }

    val selectedNotes = remember { mutableStateListOf<Long>() }
    val deleteMode = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (fetchedNotes.isEmpty() && !loading) {
            viewModel.getAllNotes()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (loading) {
            LoadingText()
        } else {
            if (fetchedNotes.isEmpty()) {
                NoNotesScreen(
                    onAddNoteClick = { navController.navigate(Screen.AddNotesScreen.name) },
                    navController = navController
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    MainTopBar(
                        onDeleteAllNotes = {
                            viewModel.deleteAllNotes()
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SearchNotesBar(
                        searchQuery = searchQuery,
                        onQueryChange = onQueryChange,
                        onClear = onClear
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(filteredNotes) { note ->
                            NoteCardItem(
                                noteEntity = note,
                                onLongClick = {
                                    // Toggle selection on long press
                                    deleteMode.value = true
                                    if (selectedNotes.contains(note.id)) {
                                        selectedNotes.remove(note.id)
                                    } else {
                                        selectedNotes.add(note.id)
                                    }
                                },
                                isSelected = selectedNotes.contains(note.id),  // Highlight selected state
                                onClick = {
                                    if (deleteMode.value) {
                                        // In delete mode, toggle the selection state
                                        if (selectedNotes.contains(note.id)) {
                                            selectedNotes.remove(note.id)
                                        } else {
                                            selectedNotes.add(note.id)
                                        }
                                    } else {
                                        // Normal click to view note details
                                        coroutineScope.launch { viewModel.getNoteById(note.id) }
                                        navController.navigate(NavigationItem.NoteDetailScreen.createRoute(note.id.toString()))
                                    }
                                }
                            )
                        }
                    }
                }
                if (selectedNotes.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomEnd)
                            .padding(16.dp, 55.dp)
                    ) {
                        IconButton(
                            onClick = {
                                selectedNotes.forEach { id ->
                                    viewModel.deleteById(id)
                                }
                                selectedNotes.clear()
                                deleteMode.value = false
                            },
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp) // Icon size
                            )
                        }
                    }
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddNotesScreen.name) },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp, 55.dp)
                        .size(56.dp)
                        .border(1.dp, Color.White, shape = FloatingActionButtonDefaults.largeShape),
                    containerColor = Color.Black
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        }
    }
}

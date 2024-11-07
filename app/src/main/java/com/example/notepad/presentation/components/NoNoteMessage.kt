package com.example.notepad.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NoNotesScreen(
    onAddNoteClick: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        NotesTopBar(navController = navController,
            onSave = {},
            isSaveIconVisible = false
        )
        SearchNotesBar(
            searchQuery = "",
            onQueryChange = {},
            onClear = {}
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add your first note!",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = onAddNoteClick),
                textAlign = TextAlign.Center
            )
        }
    }
}

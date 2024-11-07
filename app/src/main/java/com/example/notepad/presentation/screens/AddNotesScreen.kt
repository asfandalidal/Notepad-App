package com.example.notepad.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notepad.data.local.notes.entity.NoteEntity
import com.example.notepad.data.local.notes.entity.getCurrentDateTime
import com.example.notepad.presentation.components.NotepadEditor
import com.example.notepad.presentation.components.NotesTopBar
import com.example.notepad.presentation.components.TitleTextField
import com.example.notepad.presentation.viewmodels.NotesViewModel
import kotlinx.coroutines.launch

@Composable
fun AddNotesScreen(
    viewModel: NotesViewModel =  hiltViewModel(),
    navController: NavController
) {

    val titleState = rememberSaveable { mutableStateOf("") }
    val noteContentState = rememberSaveable { mutableStateOf("") }
    val isSaveIconVisible = titleState.value.isNotBlank() || noteContentState.value.isNotBlank()
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        NotesTopBar(
            onSave = {
                if (titleState.value.isNotBlank() || noteContentState.value.isNotBlank()) {
                    val note = NoteEntity(
                        date = getCurrentDateTime(),
                        title = titleState.value,
                        desc = noteContentState.value
                    )
                    coroutineScope.launch {
                        viewModel.insertNote(note)
                        navController.navigateUp()
                    }
                }
                },
                isSaveIconVisible = isSaveIconVisible,
                navController = navController
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                getCurrentDateTime(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                textAlign = TextAlign.Start,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            TitleTextField(
                titleText = titleState.value,
                onTitleChange = { titleState.value = it }
            )

            Spacer(modifier = Modifier.height(5.dp))

            NotepadEditor(
                text = noteContentState.value,
                onTextChange = { noteContentState.value = it },
                modifier = Modifier.padding(start = 7.dp)
                .focusRequester(focusRequester)
            )
        }
    }
}

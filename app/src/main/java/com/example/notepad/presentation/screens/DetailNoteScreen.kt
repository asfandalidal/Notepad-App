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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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

@Composable
fun DetailNoteScreen(
    noteEntity: NoteEntity,
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavController
) {

    val titleState = remember { mutableStateOf(TextFieldValue(noteEntity.title)) }
    val noteContentState = remember { mutableStateOf(TextFieldValue(noteEntity.desc)) }

    val isSaveIconVisible =
        titleState.value.text.isNotBlank() || noteContentState.value.text.isNotBlank()
    val focusRequester = remember { FocusRequester() }

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
                    if (isSaveIconVisible) {
                        val note = noteEntity.copy(
                            title = titleState.value.text,
                            desc = noteContentState.value.text,
                            date = getCurrentDateTime()
                        )
                        viewModel.updateNoteById(
                            noteEntity.id,
                            note.title,
                            note.desc,
                            note.date
                        )
                        navController.navigateUp()
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
                    noteEntity.date,
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
                    titleText = titleState.value.text,
                    onTitleChange = { newText ->
                        titleState.value = TextFieldValue(newText)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                NotepadEditor(
                    text = noteContentState.value.text,
                    onTextChange = { newText ->
                        noteContentState.value = TextFieldValue(newText)
                    },
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .focusRequester(focusRequester)
                )
            }
        }
    }

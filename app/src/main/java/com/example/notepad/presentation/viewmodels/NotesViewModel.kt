package com.example.notepad.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.local.notes.entity.NoteEntity
import com.example.notepad.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
):ViewModel() {

    private val _fetchedNotes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val fetchedNotes: StateFlow<List<NoteEntity>> = _fetchedNotes

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading


    fun insertNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            notesRepository.insertNote(noteEntity)

        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _loading.value = true
            notesRepository.getAllNotes().collect { items ->
                _fetchedNotes.value = items
                _loading.value = false
            }
        }
    }

}
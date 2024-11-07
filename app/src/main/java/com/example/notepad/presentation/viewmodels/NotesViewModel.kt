package com.example.notepad.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad.data.local.notes.entity.NoteEntity
import com.example.notepad.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _fetchedNotes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val fetchedNotes: StateFlow<List<NoteEntity>> = _fetchedNotes

    private val _selectedFetchedNotes = MutableStateFlow<NoteEntity?>(null)
    val selectedFetchedNotes: StateFlow<NoteEntity?> = _selectedFetchedNotes

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init {
        getAllNotes()
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

    fun insertNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            notesRepository.insertNote(noteEntity)
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            _selectedFetchedNotes.value = notesRepository.getNoteById(id.toString())
        }
    }

    fun updateNoteById(id: Long, title: String, desc: String, date: String) {
        viewModelScope.launch {
            notesRepository.updateNoteById(id.toString(), title, desc, date)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            notesRepository.deleteAllNote()
        }
    }
}


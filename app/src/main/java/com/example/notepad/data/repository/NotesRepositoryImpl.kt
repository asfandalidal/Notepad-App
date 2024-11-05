package com.example.notepad.data.repository

import android.util.Log
import com.example.notepad.data.local.notes.dao.NoteDao
import com.example.notepad.data.local.notes.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
):NotesRepository {

    override suspend fun insertNote(noteEntity: NoteEntity) {
        try {
            val id = noteDao.insertNote(noteEntity)
            Log.d("success", "Note insert successfully with ID: $id")
        } catch (e: Exception) {
            Log.e("error", e.localizedMessage ?: "Unknown error")
        }
    }


    override suspend fun updateNote(noteId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNote() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): Flow<List<NoteEntity>> {
        return try {
            val fetchedNotes = noteDao.getAllNotes()
            Log.d("success", "$fetchedNotes")
            fetchedNotes
        } catch (e: Exception) {
            Log.e("error", e.localizedMessage ?: "Unknown error")
            emptyFlow()
        }
    }
}
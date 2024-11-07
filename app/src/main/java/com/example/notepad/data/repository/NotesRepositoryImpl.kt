package com.example.notepad.data.repository

import android.util.Log
import com.example.notepad.data.local.notes.dao.NoteDao
import com.example.notepad.data.local.notes.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepository {

    override suspend fun insertNote(noteEntity: NoteEntity) {
        try {
            val id = noteDao.insertNote(noteEntity)
            Log.d("success", "Note insert successfully with ID: $id")
        } catch (e: Exception) {
            Log.e("error", e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun deleteAllNote() {
        noteDao.deleteAllNotes()
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(id: String): NoteEntity? {
        return try {
            val fetchedNote = id.toLongOrNull()?.let { noteDao.getNoteById(it) }
            Log.d("success", "$fetchedNote")
            fetchedNote
        } catch (e: Exception) {
            Log.e("error", e.localizedMessage ?: "Unknown error")
            return null
        }
    }

    override suspend fun updateNoteById(id: String, title: String, desc: String, date: String) {
        try {
            noteDao.updateNoteById(id.toLong(), title = title, desc = desc, date = date)
            Log.d("success", "Update sucessful with id : $id")
        } catch (e: Exception) {
            Log.e("error", e.localizedMessage ?: "Unknown error")
        }
    }
}
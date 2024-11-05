package com.example.notepad.data.repository

import com.example.notepad.data.local.notes.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(noteEntity: NoteEntity) //insert note into db

    suspend fun updateNote(noteId:String) //update note acc to id

    suspend fun deleteAllNote() //delete all notes

    suspend fun getAllNotes(): Flow<List<NoteEntity>> // fetch all notes from db

}
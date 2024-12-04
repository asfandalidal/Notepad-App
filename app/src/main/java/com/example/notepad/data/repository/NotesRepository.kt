package com.example.notepad.data.repository

import com.example.notepad.data.local.notes.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(noteEntity: NoteEntity) //insert note into db

    suspend fun deleteAllNote() //delete all notes

    fun getAllNotes(): Flow<List<NoteEntity>> // fetch all notes from db

    suspend fun getNoteById(id:String):NoteEntity?

    suspend fun updateNoteById(id:String,title:String,desc:String,date:String)

    suspend fun deleteById(id:String)
}
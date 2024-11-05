package com.example.notepad.data.local.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notepad.data.local.notes.dao.NoteDao
import com.example.notepad.data.local.notes.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase:RoomDatabase()
{
    abstract fun noteDao(): NoteDao
}
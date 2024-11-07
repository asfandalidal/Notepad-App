package com.example.notepad.di.provide

import android.content.Context
import androidx.room.Room
import com.example.notepad.data.local.notes.dao.NoteDao
import com.example.notepad.data.local.notes.database.NoteDatabase
import com.example.notepad.data.repository.NotesRepository
import com.example.notepad.data.repository.NotesRepositoryImpl
import com.example.notepad.utils.NoteDatabaseConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModules {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = context,
            NoteDatabase::class.java,
            NoteDatabaseConst.DB_NAME
        ).build()

    }
    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao
    {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NotesRepository {
        return NotesRepositoryImpl(noteDao)
    }


}
package com.example.notepad.data.local.notes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notepad.data.local.notes.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity):Long

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes ORDER BY date DESC")
     fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("Select* from notes where id = :id")
    suspend fun getNoteById(id:Long):NoteEntity?

    @Query("UPDATE notes SET title = :title, `desc` = :desc, date = :date WHERE id = :id")
    suspend fun updateNoteById(id: Long, title: String, desc: String, date: String)

}
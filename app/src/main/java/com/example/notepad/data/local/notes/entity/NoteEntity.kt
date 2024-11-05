package com.example.notepad.data.local.notes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notepad.utils.NoteDatabaseConst
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(NoteDatabaseConst.TABLE_NOTE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val title: String,
    val desc: String
)

fun getCurrentDateTime(): String {
    val currentDate = java.util.Date()
    val format = SimpleDateFormat("MMMM d, yyyy hh:mm a", Locale.getDefault())
    return format.format(currentDate)
}
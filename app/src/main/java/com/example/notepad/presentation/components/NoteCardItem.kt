package com.example.notepad.presentation.components


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notepad.data.local.notes.entity.NoteEntity

@Composable
fun NoteCardItem(
    noteEntity: NoteEntity,
    modifier: Modifier = Modifier,
    onClick:()->Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
             colors= CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = noteEntity.desc,
                color = Color.LightGray,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
                    .fillMaxWidth()
            )
            Text(
                text = noteEntity.date,
                color = Color.DarkGray,
                fontSize = 12.sp)
                 modifier.fillMaxWidth()
        }
    }
}

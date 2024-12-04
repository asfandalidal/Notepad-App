package com.example.notepad.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCardItem(
    noteEntity: NoteEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean,
    onLongClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color.Gray else Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Show checkmark if the item is selected
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Text(
                text = noteEntity.desc,
                color = Color.LightGray,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = noteEntity.date,
                color = Color.DarkGray,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

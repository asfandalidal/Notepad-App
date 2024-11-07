package com.example.notepad.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotepadEditor(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .background(Color.Black)
            .padding(start = 7.dp, top = 0.dp),
            contentAlignment= Alignment.TopStart
    ) {
        if (text.isEmpty()) {
            Text(
                text = "Note something down...",
                color = Color.LightGray,
                style = TextStyle(fontSize = 18.sp, lineHeight = 24.sp)
            )
        }
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            textStyle = TextStyle(fontSize = 18.sp, lineHeight = 24.sp, color = Color.White),
            modifier = Modifier.fillMaxSize(),
            cursorBrush = SolidColor(Color.White),
        )
    }
}

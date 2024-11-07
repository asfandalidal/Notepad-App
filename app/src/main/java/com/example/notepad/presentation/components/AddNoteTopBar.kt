package com.example.notepad.presentation.components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar(
    navController: NavController,
    isSaveIconVisible: Boolean,
    onSave: () -> Unit
) {
    val activity = LocalContext.current as? Activity

    Surface(
        modifier = Modifier
            .background(Color.Black)
    ) {
        TopAppBar(
            title = {
                Text(
                    "Notes",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = {
                    if (!navController.popBackStack()) {
                        activity?.finish()
                    }
                }) {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        colorFilter = ColorFilter.tint(Color.Green)
                    )
                }
            },
            actions = {
                if (isSaveIconVisible) {
                    IconButton(onClick = { onSave() }) {
                        Image(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                            colorFilter = ColorFilter.tint(Color.Green)
                        )
                    }
                }
            }
        )
    }
}

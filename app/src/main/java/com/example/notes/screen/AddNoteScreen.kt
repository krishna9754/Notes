package com.example.notes.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.components.TopBar
import com.example.notes.mvvm.NoteData
import com.example.notes.mvvm.NoteViewModel

@Composable
fun AddNoteScreen(
    noteViewModel: NoteViewModel,
    navController: NavHostController,
) = TopBar(
    topText = R.string.add_note,
    content = {
        var title by remember { mutableStateOf("") }
        var note by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { newText ->
                    title = newText.split(" ")
                        .joinToString(" ") { word ->
                            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                .lowercase()
                                .replaceFirstChar { it.uppercase() }
                        }
                },
                placeholder = {
                    Text(
                        text = "Note Title",
                        fontSize = 20.sp,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(Color.White)
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                value = note,
                onValueChange = { newText ->
                    note = newText.split(" ")
                        .joinToString(" ") { word ->
                            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                .lowercase()
                                .replaceFirstChar { it.uppercase() }
                        }
                },
                placeholder = { Text(text = "Add Your Note here ...", fontSize = 16.sp)},
                minLines = 8,
                colors = OutlinedTextFieldDefaults.colors(Color.White)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!title.isNullOrEmpty() && !note.isNullOrEmpty()) {
                        val newNote = NoteData(title = title, content = note, date = "dd/mm/yyyy")
                        noteViewModel.saveNote(newNote)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                enabled = title.isNotEmpty() && note.isNotEmpty()
            ) {
                Text("Save Note")
            }
        }
    },
    noteViewModel = noteViewModel,
    navController = navController,
    selection = false
)
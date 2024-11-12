@file:OptIn(ExperimentalMaterialApi::class)

package com.example.notes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notes.components.TopBar
import com.example.notes.mvvm.NoteData
import com.example.notes.mvvm.NoteViewModel
import com.example.notes.screen.items.DeleteConfirmationBottomSheet
import com.example.notes.screen.items.SwipeToDismissNote
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(noteViewModel: NoteViewModel, navController: NavHostController) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var noteToDelete by remember { mutableStateOf<NoteData?>(null) }
    val scope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(-1) }
    var search by remember { mutableStateOf("") }

    // Observe the search results or display all notes if search is empty
    val notes by if (search.isBlank()) {
        noteViewModel.getAllNotes().observeAsState(emptyList())
    } else {
        noteViewModel.searchQuery(search).observeAsState(emptyList())
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            noteToDelete?.let { note ->
                DeleteConfirmationBottomSheet(
                    onDismiss = {
                        scope.launch {
                            bottomSheetState.hide()
                        }
                    },
                    onConfirm = {
                        noteViewModel.deleteNote(note)
                        noteToDelete = null
                        scope.launch { bottomSheetState.hide() }
                    },
                    noteSize = note
                )
            }
        }
    ) {
        TopBar(
            navController = navController,
            arrow = false,
            content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = { Text("Search here...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(8.dp)),
                        singleLine = true,
                    )
                    Text(
                        text = "<--------  Slide Left To Delete the Note <--------",
                        modifier = Modifier.padding(top = 15.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        itemsIndexed(notes) { index, note ->
                            SwipeToDismissNote(
                                scope = scope,
                                dismissState = rememberDismissState { dismissValue ->
                                    if (dismissValue == DismissValue.DismissedToEnd) {
                                        noteToDelete = note
                                        scope.launch { bottomSheetState.show() }
                                    }
                                    true
                                },
                                note = note,
                                onDelete = {
                                    noteToDelete = note
                                    scope.launch { bottomSheetState.show() }
                                },
                                onSelect = { selectedIndex = index }
                            )
                        }
                    }
                }
            },
            noteViewModel = noteViewModel
        )
    }
}

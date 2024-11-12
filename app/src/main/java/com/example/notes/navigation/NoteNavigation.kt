package com.example.notes.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.mvvm.NoteViewModel
import com.example.notes.screen.AddNoteScreen
import com.example.notes.screen.NoteScreen

@Composable
fun NoteNavigation() {

    val noteViewModel: NoteViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NoteEnum.NOTE_SCREEN.name) {

        composable(NoteEnum.NOTE_SCREEN.name) {
            NoteScreen(
                noteViewModel = noteViewModel,
                navController = navController
            )
        }

        composable(NoteEnum.ADD_NOTE.name) {
            AddNoteScreen(noteViewModel = noteViewModel, navController = navController)
        }
    }
}
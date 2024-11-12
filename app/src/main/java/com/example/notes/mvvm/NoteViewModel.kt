package com.example.notes.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor (private val noteRepository: NoteRepository) : ViewModel() {

    fun saveNote(newNote: NoteData) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.addNote(newNote)
    }

    fun updateNote(existedNote: NoteData) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNote(existedNote)
    }

    fun deleteNote(existedNote: NoteData) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(existedNote)
    }

    fun searchQuery(query: String): LiveData<List<NoteData>> {
        return noteRepository.searchQuery(query)
    }

    fun getAllNotes(): LiveData<List<NoteData>> { return noteRepository.getNote() }
}
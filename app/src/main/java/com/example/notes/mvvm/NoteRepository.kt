package com.example.notes.mvvm

import androidx.room.Query
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDB: NoteDB) {

    fun getNote() = noteDB.getNoteDao().getAllNotes()
    fun searchQuery(query: String) = noteDB.getNoteDao().searchQuery(query)
    suspend fun addNote(noteData: NoteData) = noteDB.getNoteDao().addNotes(noteData)
    suspend fun deleteNote(noteData: NoteData) = noteDB.getNoteDao().deleteNote(noteData)
    suspend fun updateNote(noteData: NoteData) = noteDB.getNoteDao().updateNote(noteData)
}
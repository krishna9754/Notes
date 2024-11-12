package com.example.notes.mvvm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotes(noteData: NoteData)

    @Update
    suspend fun updateNote(noteData: NoteData)

    @Query("SELECT * FROM NoteData ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<NoteData>>

    @Query("SELECT * FROM NoteData WHERE title LIKE :query OR content LIKE :query OR date LIKE :query ORDER BY id DESC")
    fun searchQuery(query: String): LiveData<List<NoteData>>

    @Delete
    suspend fun deleteNote(noteData: NoteData)
}
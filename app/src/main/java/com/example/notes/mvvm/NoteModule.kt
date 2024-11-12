package com.example.notes.mvvm

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDB(@ApplicationContext context: Context): NoteDB {
        return NoteDB.invoke(context)
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDB: NoteDB): NoteDao {
        return noteDB.getNoteDao()
    }
}

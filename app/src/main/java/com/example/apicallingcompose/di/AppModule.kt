package com.example.apicallingcompose.di

import android.app.Application
import androidx.room.Room
import com.example.apicallingcompose.data.NoteDatabase
import com.example.apicallingcompose.data.NoteRepository
import com.example.apicallingcompose.domain.*
import com.example.apicallingcompose.domain.usecases.AddNoteUseCase
import com.example.apicallingcompose.domain.usecases.DeleteNoteUseCase
import com.example.apicallingcompose.domain.usecases.GetNoteUseCase
import com.example.apicallingcompose.domain.usecases.GetNotesUseCase
import com.example.apicallingcompose.domain.usecases.NoteUseCases
import com.example.apicallingcompose.domain.usecases.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "notes_db"
        ).addMigrations(NoteDatabase.MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            getNote = GetNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            updateNote = UpdateNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository)
        )
    }
}
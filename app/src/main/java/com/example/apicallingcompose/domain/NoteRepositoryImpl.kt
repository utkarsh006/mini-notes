package com.example.apicallingcompose.domain

import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.data.NoteDao
import com.example.apicallingcompose.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(id: String): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }

    override suspend fun deleteNoteById(id: String) {
        return noteDao.deleteNoteById(id)
    }
}
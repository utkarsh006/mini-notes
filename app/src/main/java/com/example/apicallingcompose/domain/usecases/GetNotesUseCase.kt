package com.example.apicallingcompose.domain.usecases

import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }
}
package com.example.apicallingcompose.domain.usecases

import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.data.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: String): Note? {
        return noteRepository.getNoteById(noteId)
    }
}
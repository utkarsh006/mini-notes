package com.example.apicallingcompose.domain.usecases

import com.example.apicallingcompose.data.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: String) {
        noteRepository.deleteNoteById(noteId)
    }
}
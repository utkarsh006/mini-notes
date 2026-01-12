package com.example.apicallingcompose.domain.usecases

import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.data.NoteRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank()) {
            throw Exception("Title of the Note can't be Empty!")
        }

        if (note.content.isBlank()) {
            throw Exception("Content of the Note can't be Empty!")
        }

        noteRepository.insertNote(note.copy(updatedAt = LocalDateTime.now()))
    }
}
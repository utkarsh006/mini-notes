package com.example.apicallingcompose.domain.usecases

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNote: GetNoteUseCase,
    val addNote: AddNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val deleteNote: DeleteNoteUseCase
)
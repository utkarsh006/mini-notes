package com.example.apicallingcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.domain.usecases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    val notes = noteUseCases.getNotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            try {
                val note = Note(
                    id = UUID.randomUUID().toString(),
                    title = title.trim(),
                    content = content.trim()
                )
                noteUseCases.addNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                noteUseCases.updateNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            try {
                noteUseCases.deleteNote(noteId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
package com.example.apicallingcompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val status: NoteStatus = NoteStatus.NOT_STARTED
)

enum class NoteStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}
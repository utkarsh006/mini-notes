package com.example.apicallingcompose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apicallingcompose.data.Note
import com.example.apicallingcompose.data.NoteStatus
import com.example.apicallingcompose.presentation.NotesViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddNoteDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No notes yet. Tap + to add one!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { editingNote = note },
                        onDelete = { viewModel.deleteNote(note.id) }
                    )
                }
            }
        }
    }

    if (showAddNoteDialog) {
        AddEditNoteDialog(
            note = null,
            onDismiss = { showAddNoteDialog = false },
            onSave = { title, content, status ->
                viewModel.addNote(title, content, status)
                showAddNoteDialog = false
            }
        )
    }

    editingNote?.let { note ->
        AddEditNoteDialog(
            note = note,
            onDismiss = { editingNote = null },
            onSave = { title, content, status ->
                viewModel.updateNote(note.copy(title = title, content = content, status = status))
                editingNote = null
            }
        )
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = note.status.name.replace("_", " "),
                            style = MaterialTheme.typography.bodySmall,
                            color = when (note.status) {
                                NoteStatus.NOT_STARTED -> MaterialTheme.colorScheme.error
                                NoteStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
                                NoteStatus.COMPLETED -> MaterialTheme.colorScheme.tertiary
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note.updatedAt.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteDialog(
    note: Note?,
    onDismiss: () -> Unit,
    onSave: (String, String, NoteStatus) -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var status by remember { mutableStateOf(note?.status ?: NoteStatus.NOT_STARTED) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (note == null) "Add Note" else "Edit Note")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Status", style = MaterialTheme.typography.labelMedium)
                Column(modifier = Modifier.fillMaxWidth()) {
                    NoteStatus.values().forEach { statusOption ->
                        FilterChip(
                            selected = status == statusOption,
                            onClick = { status = statusOption },
                            label = { Text(statusOption.name.replace("_", " ")) },
                            modifier = Modifier.padding(end = 8.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onSave(title, content, status)
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

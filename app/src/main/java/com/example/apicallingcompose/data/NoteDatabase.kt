package com.example.apicallingcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Note::class],
    version = 1
)
@TypeConverters(DateTimeConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
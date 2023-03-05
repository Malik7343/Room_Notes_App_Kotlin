package com.example.roomnotesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomnotesapp.dao.NotesDao
import com.example.roomnotesapp.data.entity.Notes


@Database(entities = arrayOf(Notes::class), version = 1)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun notesDao(): NotesDao
}
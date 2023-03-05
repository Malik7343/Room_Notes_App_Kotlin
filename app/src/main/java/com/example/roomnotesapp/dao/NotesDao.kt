package com.example.roomnotesapp.dao

import androidx.room.*
import com.example.roomnotesapp.data.entity.Notes



@Dao
interface NotesDao {

    @Query("Select * from notes ORDER BY date Desc")
    fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<Notes>>

    @Insert
    suspend fun insertNotes(notes: Notes)

    @Update
    suspend fun updateNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

}
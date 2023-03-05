package com.example.roomnotesapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomnotesapp.dao.NotesDao
import com.example.roomnotesapp.data.entity.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(private val notesDao: NotesDao):ViewModel() {

    val notes= notesDao.getAllNotes()
    val notesChannel= Channel<NotesEvent>()
    val notesEvent=notesChannel.receiveAsFlow()

    fun insertNotes(notes: Notes)=viewModelScope.launch {
        notesDao.insertNotes(notes)
        notesChannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun updateNotes(notes: Notes)=viewModelScope.launch {
        notesDao.updateNotes(notes)
        notesChannel.send(NotesEvent.NavigateToNotesFragment)
    }

    fun deleteNotes(notes: Notes)=viewModelScope.launch {
        notesDao.deleteNotes(notes)
        notesChannel.send(NotesEvent.ShowUndoSnackBar("Note Deleted Successfully",notes))
    }

    sealed class NotesEvent{
        data class ShowUndoSnackBar(val msg:String, val notes: Notes):NotesEvent()
        object NavigateToNotesFragment: NotesEvent()
    }


}
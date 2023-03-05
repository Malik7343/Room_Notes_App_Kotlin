package com.example.roomnotesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomnotesapp.R
import com.example.roomnotesapp.data.entity.Notes
import com.example.roomnotesapp.databinding.FragmentAddeditnotesBinding
import com.example.roomnotesapp.viewModel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNotesFragment : Fragment(R.layout.fragment_addeditnotes) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<NotesViewModel>()
        val binding = FragmentAddeditnotesBinding.bind((requireView()))
        val args: AddEditNotesFragmentArgs by navArgs()

        val note = args.notes

        if (note != null) {
            binding.apply {
                editTitle.setText(note.title)
                editNoteContent.setText(note.content)
                editDone.setOnClickListener {
                    val title = editTitle.text.toString()
                    val content = editNoteContent.text.toString()
                    val updateNotes = note.copy(title = title,
                        content = content,
                        date = System.currentTimeMillis())
                    viewModel.updateNotes(updateNotes)
                }
            }

        } else {
            binding.apply {
                editDone.setOnClickListener {
                    val title = editTitle.text.toString()
                    val content = editNoteContent.text.toString()
                    val note =
                        Notes(title = title, content = content, date = System.currentTimeMillis())
                    viewModel.insertNotes(note)
                }
            }


        }

           viewLifecycleOwner.lifecycleScope.launch {
               viewModel.notesEvent.collect{event->
                   val action= AddEditNotesFragmentDirections.actionAddEditNotesFragmentToNotesFragment()
                   findNavController().navigate(action)
               }
           }
    }

}


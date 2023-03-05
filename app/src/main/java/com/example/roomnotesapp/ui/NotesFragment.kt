package com.example.roomnotesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomnotesapp.R
import com.example.roomnotesapp.adapter.NotesAdapter
import com.example.roomnotesapp.data.entity.Notes
import com.example.roomnotesapp.databinding.FragmentNotesBinding
import com.example.roomnotesapp.viewModel.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes),NotesAdapter.OnNotesClickListener {

    val viewModel by viewModels<NotesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding =FragmentNotesBinding.bind((requireView()))

        binding.apply {
            notesView.layoutManager=GridLayoutManager(context,2)
            notesView.setHasFixedSize(true)

            binding.createNote.setOnClickListener {
                val action=NotesFragmentDirections.actionNotesFragmentToAddEditNotesFragment(null)
                findNavController().navigate(action)
            }

            viewLifecycleOwner.lifecycleScope.launch {
               viewModel .notes.collect{notes->
                    val adapter=NotesAdapter(notes, this@NotesFragment)
                    notesView.adapter=adapter
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notesEvent.collect{event->
                    if (event is NotesViewModel.NotesEvent.ShowUndoSnackBar){
                        Snackbar.make(requireView(),event.msg,Snackbar.LENGTH_LONG).setAction("Undo"){
                            viewModel.insertNotes(event.notes)
                        }.show()
                    }

                }
            }
        }


    }

    override fun onNotesClick(notes: Notes) {
        val action=NotesFragmentDirections.actionNotesFragmentToAddEditNotesFragment(notes)
        findNavController().navigate(action)
    }

    override fun onNotesLongClick(notes: Notes) {
        viewModel.deleteNotes(notes)
    }


}
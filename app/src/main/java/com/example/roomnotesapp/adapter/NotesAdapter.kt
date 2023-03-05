package com.example.roomnotesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomnotesapp.data.entity.Notes
import com.example.roomnotesapp.databinding.ItemsNotesBinding
import java.text.SimpleDateFormat

class NotesAdapter(private val mNotes: List<Notes>, private val listener: OnNotesClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    interface OnNotesClickListener{
        fun onNotesClick(notes: Notes)
        fun onNotesLongClick(notes: Notes)

    }

    inner class ViewHolder(private val binding: ItemsNotesBinding):RecyclerView.ViewHolder(binding.root){

        init {

            binding.apply {
                root.setOnClickListener {
                    val position=adapterPosition
                    if (position!= RecyclerView.NO_POSITION){
                        val notes=mNotes[position]
                        listener.onNotesClick(notes)
                    }
                }

                root.setOnLongClickListener{
                    val position=adapterPosition
                    if (position!= RecyclerView.NO_POSITION){
                        val notes=mNotes[position]
                        listener.onNotesLongClick(notes)
                    }
                    true
                }
            }
        }

        fun bind(notes: Notes){
            binding.apply {
                titleNote.text=notes.title
                contentNotes.text=notes.content
                val formatter=SimpleDateFormat("dd/MM/yyyy")
                dateNote.text=formatter.format(notes.date)

            }

        }

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemsNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(mNotes[position]){
           holder.bind(this)
       }
    }

    override fun getItemCount(): Int {
        return mNotes.size
    }


}
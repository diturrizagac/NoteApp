package com.diturrizaga.noteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diturrizaga.noteapp.R
import com.diturrizaga.noteapp.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter: ListAdapter<Note, NoteAdapter.NoteHolder>(DIF_CALLBACK){
    private var listener: OnItemClickListener? = null
    companion object {
        private val DIF_CALLBACK = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote)
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle = itemView.text_view_title
        var textViewDescription = itemView.text_view_description
        var textViewPriority = itemView.text_view_priority

        fun bind(note:Note){
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewPriority.text = note.priority.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
package com.diturrizaga.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.diturrizaga.noteapp.model.Note
import com.diturrizaga.noteapp.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<Note>>

    init {
        allNotes = repository.allNotes
    }

    fun insert(note:Note){
        repository.insert(note)
    }

    fun update(note:Note){
        repository.update(note)
    }

    fun delete(note:Note){
        repository.delete(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return allNotes
    }

}
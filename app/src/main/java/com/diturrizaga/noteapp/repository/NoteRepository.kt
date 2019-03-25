package com.diturrizaga.noteapp.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.diturrizaga.noteapp.model.Note
import com.diturrizaga.noteapp.room.NoteDao
import com.diturrizaga.noteapp.room.NoteDatabase

class NoteRepository {
    var noteDao: NoteDao
    var allNotes: LiveData<List<Note>>

    constructor(application: Application){
        val database =NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note:Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note:Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note:Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getNotes(): LiveData<List<Note>>{
        return allNotes
    }

    inner class InsertNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        private var noteDao = noteDao
        override fun doInBackground(vararg params: Note?): Unit? {
            noteDao.insert(params[0]!!)
            return null
        }
    }

    inner class UpdateNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        private var noteDao = noteDao
        override fun doInBackground(vararg params: Note?): Unit? {
            noteDao.update(params[0]!!)
            return null
        }
    }

    inner class DeleteNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        private var noteDao = noteDao
        override fun doInBackground(vararg params: Note?): Unit? {
            noteDao.delete(params[0]!!)
            return null
        }
    }

    inner class DeleteAllNotesAsyncTask(noteDao: NoteDao): AsyncTask<Unit, Unit, Unit>() {
        private var noteDao = noteDao
        override fun doInBackground(vararg params: Unit?): Unit? {
            noteDao.deleteAllNotes()
            return null
        }
    }
}
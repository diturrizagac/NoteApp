package com.diturrizaga.noteapp.room

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diturrizaga.noteapp.model.Note

@Database(entities = [(Note::class)] , version = 1)
abstract class NoteDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : NoteDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new)
                    .addCallback(roomCallback)
                    .build()
            }
            return instance as NoteDatabase
        }

        private val roomCallback = object:RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    //populate
    class PopulateDbAsyncTask constructor(db: NoteDatabase?): AsyncTask<Unit, Unit, Unit>() {
        private val noteDao: NoteDao = db!!.noteDao()

        override fun doInBackground(vararg p0: Unit?): Unit? {
            noteDao.insert(Note("Title 1","Description 1",1))
            noteDao.insert(Note("Title 2","Description 2", 2))
            noteDao.insert(Note("Title 3","Description 3", 3))
            return null
        }
    }
}



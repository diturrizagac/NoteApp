package com.diturrizaga.noteapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddEditNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.diturrizaga.noteapp.EXTRA_ID"
        const val EXTRA_TITLE = "com.diturrizaga.noteapp.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.diturrizaga.noteapp.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.diturrizaga.noteapp.EXTRA_PRIORITY"
    }

    lateinit var numberPickerPriority : NumberPicker
    lateinit var editTextTitle : EditText
    lateinit var editTextDescription : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)

        numberPickerPriority = findViewById(R.id.number_picker_priority)
        numberPickerPriority.minValue = 1
        numberPickerPriority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)){
            title = "Edit Note"
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPickerPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote(){
        if (editTextTitle.text.toString().trim().isBlank() || editTextDescription.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, editTextTitle.text.toString())
            putExtra(EXTRA_DESCRIPTION, editTextDescription.text.toString())
            putExtra(EXTRA_PRIORITY, numberPickerPriority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}

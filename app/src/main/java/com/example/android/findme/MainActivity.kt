package com.example.android.findme

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = createInstructionDialog()
        dialog.show()
    }

    // Private

    private fun createInstructionDialog() : Dialog {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this).apply {
            setIcon(R.drawable.android)
            setTitle(R.string.instructions_title)
            setMessage(R.string.instructions)
            setPositiveButtonIcon(ContextCompat.getDrawable(this@MainActivity, android.R.drawable.ic_media_play))

        }

        return builder.create()

    }
}
package com.example.w22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityOneBinding

class ActivityOne : AppCompatActivity() {

    private lateinit var binding: ActivityOneBinding

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurer le bouton de retour dima hotha taht onCreate
        binding.btnReturn1.setOnClickListener {
            onBackPressed()
        }

        val btnEnglish: ImageButton = findViewById(R.id.btn_english)
        val btnFrancais: ImageButton = findViewById(R.id.btn_francais)
        val btnPlay: ImageButton = findViewById(R.id.btn_play)

        // Drawable resources
        val greenDrawable = R.drawable.vert
        val redDrawable = R.drawable.rouge

        // Function to change drawable
        fun changePlayButtonDrawable(drawableId: Int) {
            btnPlay.setImageResource(drawableId)
        }

        btnEnglish.setOnClickListener {
            changePlayButtonDrawable(greenDrawable)
        }

        btnFrancais.setOnClickListener {
            changePlayButtonDrawable(greenDrawable)
        }

        btnPlay.setOnClickListener {
            // Check if the current drawable is green
            if (btnPlay.drawable.constantState == resources.getDrawable(greenDrawable, null).constantState) {
                // Open ActivityTwo if the drawable is green
                val intent = Intent(this, ActivityTwo::class.java)
                startActivityForResult(intent, 1) // Start ActivityTwo for result
            } else {
                // Do nothing if the drawable is red
                Toast.makeText(this, "Button is red, cannot open the next activity.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) { // Check if the result is from ActivityTwo
            // Set the play button drawable to red when returning
            val btnPlay: ImageButton = findViewById(R.id.btn_play)
            btnPlay.setImageResource(R.drawable.rouge)
        }
    }
}

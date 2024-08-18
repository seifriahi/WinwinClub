package com.example.w22

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityTwoBinding

class ActivityTwo : AppCompatActivity() {
    private lateinit var binding: ActivityTwoBinding

    private var isBtnNouveauClicked = false
    private var isBtnPlayClicked = false
    private var isBtnAbonneClicked = false

    companion object {
        private const val REQUEST_CODE_SIGN_UP = 1
        private const val REQUEST_CODE_SIGN_IN = 2
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurer le bouton de retour
        binding.btnReturn2.setOnClickListener {
            onBackPressed()
        }

        val btnAbonne: ImageButton = binding.btnAbonne
        val btnNouveau: ImageButton = binding.btnNouveau
        val btnPlay: ImageButton = binding.btnPlay2

        // Drawable resources
        val greenDrawable = R.drawable.vert
        val redDrawable = R.drawable.rouge

        btnAbonne.setOnClickListener {
            isBtnAbonneClicked = true
            changePlayButtonDrawable(greenDrawable)
        }

        btnNouveau.setOnClickListener {
            isBtnNouveauClicked = true
            changePlayButtonDrawable(greenDrawable)
        }

        btnPlay.setOnClickListener {
            isBtnPlayClicked = true
            if (isBtnNouveauClicked && isBtnPlayClicked) {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_SIGN_UP)
            } else if (isBtnAbonneClicked && isBtnPlayClicked) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_UP || requestCode == REQUEST_CODE_SIGN_IN) {
            // Reset btnPlay drawable to red
            changePlayButtonDrawable(R.drawable.rouge)
            isBtnPlayClicked = false
            isBtnNouveauClicked = false
            isBtnAbonneClicked = false
        }
    }

    // Function to change drawable
    private fun changePlayButtonDrawable(drawableId: Int) {
        binding.btnPlay2.setImageResource(drawableId)
    }
}

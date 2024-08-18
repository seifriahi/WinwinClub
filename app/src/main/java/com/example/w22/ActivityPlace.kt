package com.example.w22

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityPlaceBinding
import com.example.w22.databinding.ActivityPlanBinding

class ActivityPlace : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnplace.setOnClickListener {
            onBackPressed()
        }

        // Récupération de l'email depuis les préférences partagées
        val mSharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)

        // Récupération du nom depuis les préférences partagées
        val name = mSharedPreferences.getString(FULLNAME, "")
        binding.txtFullName2.text = name

        val fullname = mSharedPreferences.getString(FULLNAME, "")
        binding.txtFullName2.text = fullname

        val buttons = listOf(
            findViewById<ImageButton>(R.id.colorChangingButton),
            findViewById<ImageButton>(R.id.colorChangingButton1),
            findViewById<ImageButton>(R.id.colorChangingButton2),
            findViewById<ImageButton>(R.id.colorChangingButton3),
            findViewById<ImageButton>(R.id.colorChangingButton4),
            findViewById<ImageButton>(R.id.colorChangingButton5),
            findViewById<ImageButton>(R.id.colorChangingButton6),
            findViewById<ImageButton>(R.id.colorChangingButton7),
            findViewById<ImageButton>(R.id.colorChangingButton8),
            findViewById<ImageButton>(R.id.colorChangingButton9),
            findViewById<ImageButton>(R.id.colorChangingButton10),
            findViewById<ImageButton>(R.id.colorChangingButton11)
        )

        buttons.forEach { button ->
            var isRed = true
            button.setOnClickListener {
                if (isRed) {
                    button.setBackgroundResource(R.drawable.button_green)
                } else {
                    button.setBackgroundResource(R.drawable.button_red)
                }
                isRed = !isRed
            }
        }
    }
}

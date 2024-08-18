package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityPlanBinding

class ActivityPlan : AppCompatActivity() {

    private lateinit var binding: ActivityPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnplan.setOnClickListener {
            onBackPressed()
        }

        // Récupération de l'email depuis les préférences partagées
        val mSharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)

        // Récupération du nom depuis les préférences partagées
        val name = mSharedPreferences.getString(FULLNAME, "")
        binding.txtFullName.text = name

        val fullname = mSharedPreferences.getString(FULLNAME, "")
        binding.txtFullName.text = fullname


        // Set up the button click listeners
        binding.btnPremium.setOnClickListener {
            showConfirmDialog("Premium Pack")
        }

        binding.btnModerato.setOnClickListener {
            showConfirmDialog("Moderato Pack")
        }

        binding.btnBasic
            .setOnClickListener {
            showConfirmDialog("Basic Pack")
        }
    }

    private fun showConfirmDialog(packName: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
        val dialogCancel = dialogView.findViewById<Button>(R.id.dialog_cancel)
        val dialogConfirm = dialogView.findViewById<Button>(R.id.dialog_confirm)

        dialogMessage.text = packName

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogConfirm.setOnClickListener {
            // Handle the confirmation action
            startActivity(Intent(this, ActivityPlace::class.java)) // Replace `NextActivity` with your target activity
        }

        dialog.show()
    }


}

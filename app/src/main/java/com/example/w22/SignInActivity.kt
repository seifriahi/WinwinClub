package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar

const val PHONE2 = "PHONE2"

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
        val contextViewsignin = findViewById<View>(R.id.context_viewsignin)

        binding.etPhoneNumber2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhoneNumber()
            }
            override fun afterTextChanged(s: Editable?) {}
        })


        binding.btnPlaysignin.setOnClickListener {
            if (validatePhoneNumber()) {
                if (binding.btnPlaysignin.isClickable) {
                    sharedPreferences.edit().apply {
                        putString(PHONE2, binding.etPhoneNumber2.text.toString())
                    }.apply()
                    startActivity(Intent(this, ActivityPlan::class.java)) // Replace `NextActivity` with your target activity

                    finish()


                }
            } else {
                Snackbar.make(contextViewsignin, getString(R.string.msg_error_inputs), Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.btnReturnsignup.setOnClickListener {
            finish()
        }

    }







    private fun validatePhoneNumber(): Boolean {
        binding.tiPhoneNumberLayout2.isErrorEnabled = false
        return if (binding.etPhoneNumber2.text.toString().isEmpty()) {
            binding.tiPhoneNumberLayout2.error = getString(R.string.msg_must_not_be_empty)
            false
        } else {
            true
        }
    }
}




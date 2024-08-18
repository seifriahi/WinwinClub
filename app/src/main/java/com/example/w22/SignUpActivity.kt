package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.example.w22.databinding.ActivitySignUpBinding

const val FULLNAME = "FULLNAME"
const val EMAIL = "EMAIL"
const val PHONE = "PHONE"
const val FIELDOF = "FIELDOF"
const val PREF_FILE = "PREF_FILE"

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contextView = findViewById<View>(R.id.context_view1) // Assuming you have this view in your layout
        val sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)

        binding.etFullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFullName()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etFieldOfActivity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFieldOfActivity()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePhoneNumber()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnPlaysignup.setOnClickListener {
            if (validateFullName() && validateEmail() && validateFieldOfActivity() && validatePhoneNumber()) {
                if (binding.btnPlaysignup.isClickable) {
                    sharedPreferences.edit().apply {
                        putString(FULLNAME, binding.etFullName.text.toString())
                        putString(EMAIL, binding.etEmail.text.toString())
                        putString(PHONE, binding.etPhoneNumber.text.toString())
                        putString(FIELDOF, binding.etFieldOfActivity.text.toString())
                    }.apply()
                    startActivity(Intent(this, ActivityCong::class.java)) // Replace `NextActivity` with your target activity

                    finish()
                }
            } else {
                Snackbar.make(contextView, getString(R.string.msg_error_inputs), Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnReturnsignup.setOnClickListener {
            finish()
        }
    }

    private fun validateFullName(): Boolean {
        binding.tiFullNameLayout.isErrorEnabled = false
        return if (binding.etFullName.text.toString().isEmpty()) {
            binding.tiFullNameLayout.error = getString(R.string.msg_must_not_be_empty)
            false
        } else if (binding.etFullName.text.toString().length < 6) {
            binding.tiFullNameLayout.error = getString(R.string.msg_check_your_characters)
            false
        } else {
            true
        }
    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.isErrorEnabled = false
        return if (binding.etEmail.text.toString().isEmpty()) {
            binding.tiEmailLayout.error = getString(R.string.msg_must_not_be_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
            binding.tiEmailLayout.error = getString(R.string.msg_check_your_email)
            false
        } else {
            true
        }
    }

    private fun validateFieldOfActivity(): Boolean {
        binding.tiFieldOfActivityLayout.isErrorEnabled = false
        return if (binding.etFieldOfActivity.text.toString().isEmpty()) {
            binding.tiFieldOfActivityLayout.error = getString(R.string.msg_must_not_be_empty)
            false
        } else {
            true
        }
    }

    private fun validatePhoneNumber(): Boolean {
        binding.tiPhoneNumberLayout.isErrorEnabled = false
        return if (binding.etPhoneNumber.text.toString().isEmpty()) {
            binding.tiPhoneNumberLayout.error = getString(R.string.msg_must_not_be_empty)
            false
        } else {
            true
        }
    }
}


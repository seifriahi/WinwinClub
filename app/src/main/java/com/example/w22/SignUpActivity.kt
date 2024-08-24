package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.example.w22.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val FULLNAME = "FULLNAME"
const val EMAIL = "EMAIL"
const val PHONE = "PHONE"
const val FIELDOF = "FIELDOF"
const val PREF_FILE = "PREF_FILE"

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var spinnerType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerType = findViewById(R.id.spinnerType)
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@SignUpActivity, "Selected Type: $selectedType", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@SignUpActivity, "No Type Selected", Toast.LENGTH_SHORT).show()
            }
        }

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
                val fullName = binding.etFullName.text.toString()
                val email = binding.etEmail.text.toString()
                val phoneNumber = binding.etPhoneNumber.text.toString()
                val fieldOfActivity = binding.etFieldOfActivity.text.toString()
                val type = spinnerType.selectedItem.toString()

                val user = User(fullName, email, phoneNumber, fieldOfActivity, type)
                signUp(user)

                val sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
                sharedPreferences.edit().apply {
                    putString(FULLNAME, fullName)
                    putString(EMAIL, email)
                    putString(PHONE, phoneNumber)
                    putString(FIELDOF, fieldOfActivity)
                    putString("USER_TYPE", type)
                }.apply()

                startActivity(Intent(this, ActivityCong::class.java))
                finish()
            } else {
                val contextView = findViewById<View>(R.id.context_view1)
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

    private fun signUp(user: User) {
        val call = ApiClient.apiService.signUp(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "Inscription réussie!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("SignUpActivity", "Erreur : ${response.errorBody()?.string()}")
                    Toast.makeText(this@SignUpActivity, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("SignUpActivity", "Erreur : ${t.message}")
                Toast.makeText(this@SignUpActivity, "Échec de la connexion au serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


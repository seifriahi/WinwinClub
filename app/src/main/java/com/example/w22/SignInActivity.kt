package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivitySignInBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    val phoneNumber = binding.etPhoneNumber2.text.toString()
                    val request = SignInRequest(phoneNumber)
                    signIn(request)
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

    private fun signIn(request: SignInRequest) {
        ApiClient.apiService.signIn(request).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    Toast.makeText(this@SignInActivity, "Connexion réussie: ${signInResponse?.message}", Toast.LENGTH_SHORT).show()

                    // Pass the phone number to ActivityPlan
                    val phoneNumber = binding.etPhoneNumber2.text.toString()
                    val intent = Intent(this@SignInActivity, PlanActivity::class.java)
                    intent.putExtra("PHONE_NUMBER", phoneNumber)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("SignInActivity", "Erreur : ${response.errorBody()?.string()}")
                    Snackbar.make(findViewById(R.id.context_viewsignin), "Erreur lors de la connexion", Snackbar.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Log.e("SignInActivity", "Erreur : ${t.message}")
                Snackbar.make(findViewById(R.id.context_viewsignin), "Échec de la connexion au serveur", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}

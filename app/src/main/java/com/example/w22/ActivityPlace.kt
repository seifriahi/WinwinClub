package com.example.w22

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityPlaceBinding
import com.example.w22.databinding.ActivityPlanBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityPlace : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceBinding

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.20.10.11:5000/api/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // API service
    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnplace.setOnClickListener {
            onBackPressed()
        }

        val mSharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE)
        val phoneNumber = mSharedPreferences.getString(PHONE_NUMBER, "")
        val name = mSharedPreferences.getString(FULLNAME, "")
        binding.txtFullName2.text = name

        val buttons = listOf(

            findViewById<ImageButton>(R.id.placeNumber20),
            findViewById<ImageButton>(R.id.placeNumber2),
            // ... other buttons
        )

        buttons.forEachIndexed { index, button ->
            val placeNumber = (index + 1).toString()
            button.setOnClickListener {
                button.setBackgroundResource(
                    if (button.background == getDrawable(R.drawable.button_red))
                        R.drawable.button_green
                    else
                        R.drawable.button_red
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val placeRequest = PlaceRequest(phoneNumber ?: "", placeNumber)
                        Log.d("API_REQUEST", "Request body: $placeRequest")

                        val response = apiService.selectPlace(placeRequest)
                        if (response.isSuccessful) {
                            Log.d("API_SUCCESS", "Place selected successfully")
                        } else {
                            Log.e("API_ERROR", "Error response code: ${response.code()}")
                            Log.e("API_ERROR", "Response body: ${response.errorBody()?.string()}")
                        }
                    } catch (e: Exception) {
                        Log.e("API_EXCEPTION", "Exception: ${e.message}")
                    }
                }
            }
        }
    }
}

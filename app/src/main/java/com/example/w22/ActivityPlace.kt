package com.example.w22

import android.os.Bundle
import android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityPlaceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityPlace : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceBinding

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.147:5000/api/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // API service
    private val apiService = retrofit.create(ApiService::class.java)

    // Mapping of button IDs to place numbers
    private val placeNumberMap = mapOf(
        R.id.placeNumber20 to "20",
        R.id.placeNumber2 to "2",
        R.id.placeNumber3 to "3" // Add other mappings as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReturnplace.setOnClickListener {
            onBackPressed()
        }

        // Retrieve the phone number passed from SignInActivity
        val phoneNumber = intent.getStringExtra("PHONE_NUMBER") ?: ""
        val name = getSharedPreferences(PREF_FILE, MODE_PRIVATE).getString(FULLNAME, "")
        binding.txtFullName2.text = name

        placeNumberMap.forEach { (buttonId, placeNumber) ->
            findViewById<ImageButton>(buttonId).setOnClickListener {
                val button = it as ImageButton
                button.setBackgroundResource(
                    if (button.background == getDrawable(R.drawable.button_red))
                        R.drawable.button_green
                    else
                        R.drawable.button_red
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val placeRequest = PlaceRequest(phoneNumber, placeNumber)
                        Log.d("API_REQUEST", "Request body: $placeRequest")


                        val response = apiService.selectPlace(placeRequest)
                        if (response.isSuccessful) {
                            Log.d("API_SUCCESS", "Place selected successfully")
                        } else {
                            Log.e("API_ERROR", "Error response code: ${response.code()}")
                            Log.e("API_ERROR", "Response body: ${response.errorBody()?.string()}")
                        }

                    } catch (e: HttpException) {
                        Log.e("API_EXCEPTION", "HttpException: ${e.message}")
                    } catch (e: Exception) {
                        Log.e("API_EXCEPTION", "Exception: ${e.message}")
                    }
                }
            }
        }
    }
}

package com.example.w22

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w22.databinding.ActivityPlanBinding
import com.example.w22.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlanBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var packAdapter: PackAdapter
    private var packList: MutableList<Pack> = mutableListOf() // Use MutableList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        binding = ActivityPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextViewsignin = findViewById<View>(R.id.context_viewplan)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize the adapter with an empty list
        packAdapter = PackAdapter(packList)
        recyclerView.adapter = packAdapter

        // Fetch packs automatically when the activity is created
        fetchPacks()

        binding.btnReturplan.setOnClickListener {
            finish()
        }
    }

    private fun fetchPacks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.11:5000") // Ensure the URL is correct
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val packApi = retrofit.create(ApiService::class.java)
        packApi.getPacks().enqueue(object : Callback<ApiResponsee<List<Pack>>> { // Specify the correct type here
            override fun onResponse(call: Call<ApiResponsee<List<Pack>>>, response: Response<ApiResponsee<List<Pack>>>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        packList.clear() // Clear existing data
                        packList.addAll(apiResponse.data) // Add new data
                        Log.d("PlanActivity", "Fetched packs: $packList")
                        packAdapter.notifyDataSetChanged() // Update the RecyclerView
                    } else {
                        Log.e("PlanActivity", "Error: ${apiResponse?.success}")
                    }
                } else {
                    Log.e("PlanActivity", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ApiResponsee<List<Pack>>>, t: Throwable) {
                Log.e("PlanActivity", "Error fetching packs", t)
            }
        })
    }
}

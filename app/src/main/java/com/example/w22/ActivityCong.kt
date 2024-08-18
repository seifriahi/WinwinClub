package com.example.w22

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.w22.databinding.ActivityCongraBinding

class ActivityCong: AppCompatActivity() {
    private lateinit var binding: ActivityCongraBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congra)

        binding = ActivityCongraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnReturncong.setOnClickListener {
            onBackPressed()
        }
        binding.btnPlaycong.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }


    }

}
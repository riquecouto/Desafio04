package com.example.desafio04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio04.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.txtGameName.text = intent.getStringExtra("title")
        binding.txtGameName.text = intent.getStringExtra("description")

        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarDetails.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
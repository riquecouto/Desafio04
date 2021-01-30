package com.example.desafio04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio04.databinding.ActivityDetailsBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    lateinit var storageRef : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.txtGameName.text = intent.getStringExtra("title")
        binding.txtCreateDate.text = intent.getStringExtra("createDate")
        binding.txtDescription.text = intent.getStringExtra("description")

        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarDetails.setNavigationOnClickListener {
            onBackPressed()
        }


        if (intent.getStringExtra("imgRef")?.isNotEmpty() == true){
            storageRef = FirebaseStorage.getInstance().getReference(intent.getStringExtra("imgRef")!!)

            GlideApp.with(this).asBitmap()
                .load(storageRef)
                .into(binding.imgGameDetail)
        }
        else {
            binding.imgGameDetail.setImageResource(R.drawable.noimage)
        }

    }
}
package com.example.desafio04

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafio04.databinding.ActivityIncludeGameBinding
import java.util.*

class IncludeGameActivity : BaseActivity() {

    private lateinit var binding: ActivityIncludeGameBinding
    private lateinit var title: String
    private lateinit var createDate: String
    private lateinit var description: String
    private var img: String = ""
    private  var imgRef: String =""

    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_include_game)

        binding = ActivityIncludeGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setProgressBar(binding.progressBar)

        binding.gameImgBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Img"), 1000)
        }

        binding.btnSave.setOnClickListener {
            val game = getFormFields()
            viewModel.sendGame(game)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imgRef = Random().nextInt().toString()

        if (data != null) {
            viewModel.uploadImage(data, imgRef)
        }

        viewModel.urlImage.observe(this, {
            if (viewModel.urlImage.value != null) {
                img = viewModel.urlImage.value!!.toString()
            }
        })
    }

    private fun getFormFields(): Games {
        title = binding.edTitle.text.toString()
        createDate = binding.createdAt.text.toString()
        description = binding.edDescription.text.toString()
        return Games(title, createDate, description, img, imgRef)
    }
}
package com.example.desafio04

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio04.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameAdapter: GamesAdapter

    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar(binding.progressBar)


        viewModel.sendGame(
            Games(
                "X-MEN",
                2009,
                "Mutantes",
                "https://upload.wikimedia.org/wikipedia/pt/6/6c/God_of_War_3_capa.png"
            )
        )

        viewModel.getGames()

        viewModel.gamesList.observe(this) {
            gameAdapter = GamesAdapter(it, View.OnClickListener {
//                startActivity(Intent(this, GamesDetailActivity::class.java))
            })
            binding.rcGames.adapter = gameAdapter
            binding.rcGames.layoutManager = GridLayoutManager(this@MainActivity,2)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.fab -> callNewGame()
            }
        }
    }

    private fun callNewGame() {
        startActivity(Intent(this, IncludeGameActivity::class.java))
    }
}
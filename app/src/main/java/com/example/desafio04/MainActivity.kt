package com.example.desafio04

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafio04.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), onClickListener {

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

//        viewModel.sendGame(
//            Games(
//                "X-MEN",
//                2009.toString(),
//                "Mutantes",
//                "https://upload.wikimedia.org/wikipedia/pt/6/6c/God_of_War_3_capa.png"
//            )
//        )

        viewModel.getGames()

        viewModel.gamesList.observe(this) {
            gameAdapter = GamesAdapter(it, this)
                    binding.rcGames.adapter = gameAdapter
        }

//        binding.rcGames.adapter = gameAdapter
        binding.rcGames.layoutManager = GridLayoutManager(this@MainActivity, 2)
        binding.fab.setOnClickListener {
            callNewGame()
        }
    }

    private fun callNewGame() {
        startActivity(Intent(this, IncludeGameActivity::class.java))
    }

    override fun gameClick(game: Games) {
        viewModel.gamesList.observe(this, {

            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        "title" to game.title,
                        "createDate" to game.createDate,
                        "description" to game.description,
                        "imgRef" to game.imgRef
                    )
                )
            }
            startActivity(intent)
        })
    }
}
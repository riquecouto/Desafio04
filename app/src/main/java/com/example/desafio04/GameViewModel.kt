package com.example.desafio04

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel() : ViewModel() {

    var gamesList = MutableLiveData<ArrayList<Games>>()

    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )


    fun sendGame(game: Games) {
        db.collection("games")
            .add(game)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Game added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding Game", e)
            }
    }

    fun getGames() {
        db.collection("games")
            .get()
            .addOnSuccessListener { result ->

                val resultList = arrayListOf<Games>()

                for (game in result) {
                    Log.d(TAG, "${game.id} => ${game.data}")
                    resultList.add(game.toObject(Games::class.java))
//                    resultList.add(game)
                }
                gamesList.value = resultList
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting games", exception)
            }
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
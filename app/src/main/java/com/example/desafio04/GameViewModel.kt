package com.example.desafio04

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    var gamesList = MutableLiveData<ArrayList<Games>>()
    var urlImage: MutableLiveData<String> = storage.imgURL

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

    fun uploadImage(data: Intent, location: String) {
        viewModelScope.launch {
            storage.uploadImg(data, location)
        }
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
package com.example.desafio04

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStorage {

    lateinit var storageRef : StorageReference
    var imgURL: MutableLiveData<String> = MutableLiveData()

    fun uploadImg(data: Intent, location: String) {
        getReferenceStorage(location)
        val uploadFile = storageRef.putFile(data.data!!)
        val task = uploadFile.continueWithTask { task ->
            if (task.isSuccessful) {
            }
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                imgURL.value =
//                    downloadUri!!.toString().substring(0, downloadUri.toString().indexOf("&token"))
                    downloadUri!!.toString()
            }
        }
    }

    private fun getReferenceStorage(location: String) {
        storageRef = FirebaseStorage.getInstance().getReference(location)
    }
}
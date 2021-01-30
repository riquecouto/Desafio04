package com.example.desafio04

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

val db = Firebase.firestore
//val cr: CollectionReference = db.collection("Games")
val storage:  FirebaseStorage = FirebaseStorage()
package com.example.desafio04

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.desafio04.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : BaseActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar(binding.progressBar)

        binding.btnSave.setOnClickListener(this)

        auth = Firebase.auth
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> createAccount(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(LoginActivity.TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }
        showProgressBar()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    callHome()
                } else {
                    Toast.makeText(
                        baseContext, "Failed to create",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideProgressBar()
            }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = binding.edEmail.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.edEmail.error = "Required"
            valid = false
        } else {
            binding.edEmail.error = null
        }

        val password = binding.edPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.edPassword.error = "Required"
            valid = false
        } else {
            binding.edPassword.error = null
        }
        return valid
    }

    private fun callHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
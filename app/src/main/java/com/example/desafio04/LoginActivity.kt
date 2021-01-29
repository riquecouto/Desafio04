package com.example.desafio04

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.desafio04.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar(binding.progressBar)

        binding.btnLogIn.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            auth.currentUser!!.reload().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "reload")
                } else {
                    Log.e(TAG, "reload", task.exception)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnSignUp -> callSignup()
                R.id.btnLogIn -> signIn(
                    binding.edEmail.text.toString(),
                    binding.edPassword.text.toString()
                )
            }
        }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        showProgressBar()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    callHome()
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed",
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

    private fun callSignup() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}
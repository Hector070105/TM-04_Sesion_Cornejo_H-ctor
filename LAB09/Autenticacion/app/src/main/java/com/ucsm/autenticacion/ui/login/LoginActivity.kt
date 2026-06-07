package com.ucsm.autenticacion.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.ucsm.autenticacion.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    private var semail: String = ""
    private var spassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = binding.username
        val password = binding.password
        val login = binding.login

        binding.login.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            performLoginOrRegistration()
        }
    }

    private fun performLoginOrRegistration() {
        semail = binding.username.text.toString().trim()
        spassword = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            binding.username.error = "Formato inválido de email"
            binding.loading.visibility = View.GONE
        } else if (TextUtils.isEmpty(spassword) || spassword.length < 6) {
            binding.password.error = "El password debe tener al menos 6 caracteres"
            binding.loading.visibility = View.GONE
        } else {
            registerUser()
        }
    }

    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(semail, spassword)
            .addOnCompleteListener(this) { task ->

                binding.loading.visibility = View.GONE

                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Registro exitoso en Firebase",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.username.text.clear()
                    binding.password.text.clear()

                } else {
                    Toast.makeText(
                        this,
                        "Error: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
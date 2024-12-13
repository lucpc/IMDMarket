package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username == "admin" && password == "admin") {
                startActivity(Intent(this, MenuActivity::class.java))
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
            }
        }

        /*binding.btnChangePassword.setOnClickListener {
            // Implementar lógica de alteração de senha
        }*/
    }
}
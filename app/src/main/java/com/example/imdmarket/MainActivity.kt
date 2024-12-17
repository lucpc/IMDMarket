package com.example.imdmarket

import android.content.Context
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

        // Carregar as credenciais do SharedPreferences e definições de usuário padrão.
        val sharedPreferences = getSharedPreferences("IMDMarketPrefs", Context.MODE_PRIVATE)
        val defaultUsername = sharedPreferences.getString("username", "admin")
        val defaultPassword = sharedPreferences.getString("password", "admin")

        // Configuração do botão "Entrar"
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username == defaultUsername && password == defaultPassword) {
                startActivity(Intent(this, MenuActivity::class.java))
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
            }
        }

        // Configuração do botão "Alterar Senha"
        binding.tvForgotPassword.setOnClickListener {
            val newUsername = binding.etUsername.text.toString()
            val newPassword = binding.etPassword.text.toString()

            if (newUsername.isNotEmpty() && newPassword.isNotEmpty()) {
                with(sharedPreferences.edit()) {
                    putString("username", newUsername)
                    putString("password", newPassword)
                    apply()
                }
                Toast.makeText(this, "Credenciais alteradas com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Preencha usuário e senha", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
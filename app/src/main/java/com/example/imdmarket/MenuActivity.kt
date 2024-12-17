package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navegação para a Tela de Cadastro
        binding.btnCadastrar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Navegação para a Tela de Listagem
        binding.btnListar.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}

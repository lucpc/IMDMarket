package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            val code = binding.etProductCodeUpdate.text.toString()

            if (code.isEmpty()) {
                Toast.makeText(this, "O código é obrigatório", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
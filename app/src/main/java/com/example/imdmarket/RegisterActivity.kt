package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val code = binding.etProductCode.text.toString()
            val name = binding.etProductName.text.toString()
            val description = binding.etProductDescription.text.toString()
            val stock = binding.etProductStock.text.toString()

            if (code.isEmpty() || name.isEmpty() || description.isEmpty() || stock.isEmpty()) {
                Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produto cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                finish() // Volta para a tela de menu
            }
        }
    }
}

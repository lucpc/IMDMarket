package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.data.IMDMarketDatabaseHelper
import com.example.imdmarket.databinding.ActivityRegisterBinding
import com.example.imdmarket.model.Produto

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: IMDMarketDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        binding.btnSave.setOnClickListener {
            val code = binding.etProductCode.text.toString()
            val name = binding.etProductName.text.toString()
            val description = binding.etProductDescription.text.toString()
            val stock = binding.etProductStock.text.toString().toIntOrNull()

            if (code.isEmpty() || name.isEmpty() || description.isEmpty() || stock == null) {
                Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show()
            } else {
                val newProduct = Produto(code, name, description, stock)
                val success = dbHelper.addProduct(newProduct) // Tenta adicionar o produto
                if (success) {
                    Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Produto com este código já existe!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


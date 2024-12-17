package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDelete.setOnClickListener {
            val code = binding.etProductCodeDelete.text.toString()

            if (code.isEmpty()) {
                Toast.makeText(this, "O código do produto é obrigatório", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Produto excluído com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
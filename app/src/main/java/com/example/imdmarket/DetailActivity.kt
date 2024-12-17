package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.data.IMDMarketDatabaseHelper
import com.example.imdmarket.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dbHelper: IMDMarketDatabaseHelper
    private var productCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)
        productCode = intent.getStringExtra("product_code")

        loadProductDetails() // Carrega os detalhes ao iniciar

        // Config do botão Editar
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("product_code", productCode)
            startActivity(intent)
        }

        // Config do botão Excluir
        binding.btnDelete.setOnClickListener {
            productCode?.let { code ->
                val success = dbHelper.deleteProduct(code)
                if (success) {
                    Toast.makeText(this, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela de detalhamento
                } else {
                    Toast.makeText(this, "Erro ao excluir o produto!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Botão Voltar
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarrega os detalhes do produto quando a tela é reaberta
        loadProductDetails()
    }

    private fun loadProductDetails() {
        productCode?.let { code ->
            val product = dbHelper.getAllProducts().find { it.codigo == code }
            if (product != null) {
                // Exibe os detalhes atualizados
                binding.tvProductCode.text = "Código: ${product.codigo}"
                binding.tvProductName.text = "Nome: ${product.nome}"
                binding.tvProductDescription.text = "Descrição: ${product.descricao}"
                binding.tvProductStock.text = "Estoque: ${product.estoque}"
            } else {
                // Caso o produto não exista mais (excluído), fecha a tela
                Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        // Recebe o código do produto enviado como extra
        val productCode = intent.getStringExtra("product_code")

        if (productCode != null) {
            // Busca o produto no banco de dados
            val product = dbHelper.getAllProducts().find { it.codigo == productCode }

            if (product != null) {
                // Exibe os detalhes do produto
                binding.tvProductCode.text = "Código: ${product.codigo}"
                binding.tvProductName.text = "Nome: ${product.nome}"
                binding.tvProductDescription.text = "Descrição: ${product.descricao}"
                binding.tvProductStock.text = "Estoque: ${product.estoque}"

                // Configurações do botão Editar
                binding.btnEdit.setOnClickListener {
                    val intent = Intent(this, UpdateActivity::class.java)
                    intent.putExtra("product_code", product.codigo)
                    startActivity(intent)
                }

                // Configurações do botão Excluir
                binding.btnDelete.setOnClickListener {
                    val success = dbHelper.deleteProduct(product.codigo)
                    if (success) {
                        Toast.makeText(this, "Produto excluído com sucesso!", Toast.LENGTH_SHORT).show()
                        finish() // Fecha a tela e retorna para a lista
                    } else {
                        Toast.makeText(this, "Erro ao excluir o produto!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Código do produto não fornecido!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Botão para voltar à lista
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}


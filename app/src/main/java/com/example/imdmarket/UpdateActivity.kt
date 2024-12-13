package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityUpdateBinding
import com.example.imdmarket.data.IMDMarketDatabaseHelper
import com.example.imdmarket.model.Produto

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var dbHelper: IMDMarketDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        // Recebe o código do produto enviado como extra
        val productCode = intent.getStringExtra("product_code")

        if (productCode != null) {
            // Busca o produto no banco de dados
            val product = dbHelper.getAllProducts().find { it.codigo == productCode }

            if (product != null) {
                // Preenche os campos com os dados atuais
                binding.etProductNameUpdate.setText(product.nome)
                binding.etProductDescriptionUpdate.setText(product.descricao)
                binding.etProductStockUpdate.setText(product.estoque.toString())

                // Configurações do botão Salvar
                binding.btnSaveUpdate.setOnClickListener {
                    val newName = binding.etProductNameUpdate.text.toString()
                    val newDescription = binding.etProductDescriptionUpdate.text.toString()
                    val newStock = binding.etProductStockUpdate.text.toString().toIntOrNull()

                    if (newName.isEmpty() || newDescription.isEmpty() || newStock == null) {
                        Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
                    } else {
                        val updatedProduct = Produto(
                            codigo = product.codigo,
                            nome = newName,
                            descricao = newDescription,
                            estoque = newStock
                        )

                        val success = dbHelper.updateProduct(updatedProduct)
                        if (success) {
                            Toast.makeText(this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish() // Fecha a tela de edição
                        } else {
                            Toast.makeText(this, "Erro ao atualizar o produto!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // Configurações do botão Cancelar
                binding.btnCancelUpdate.setOnClickListener {
                    finish() // Volta para a tela anterior
                }
            } else {
                Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Código do produto não fornecido!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

package com.example.imdmarket

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.data.IMDMarketDatabaseHelper
import com.example.imdmarket.databinding.ActivityUpdateBinding
import com.example.imdmarket.model.Produto


class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var dbHelper: IMDMarketDatabaseHelper
    private lateinit var originalProduct: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        // Recebe o código do produto enviado como extra
        val productCode = intent.getStringExtra("product_code")

        if (productCode != null) {
            // Busca o produto no banco de dados
            originalProduct = dbHelper.getAllProducts().find { it.codigo == productCode }
                ?: run {
                    Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }

            // Preenche os campos com os dados atuais
            binding.etProductCodeUpdate.setText(originalProduct.codigo)
            binding.etProductNameUpdate.setText(originalProduct.nome)
            binding.etProductDescriptionUpdate.setText(originalProduct.descricao)
            binding.etProductStockUpdate.setText(originalProduct.estoque.toString())

            // Configuração do botão Salvar
            binding.btnSaveUpdate.setOnClickListener {
                val newCode = binding.etProductCodeUpdate.text.toString()
                val newName = binding.etProductNameUpdate.text.toString()
                val newDescription = binding.etProductDescriptionUpdate.text.toString()
                val newStock = binding.etProductStockUpdate.text.toString().toIntOrNull()

                if (newCode.isEmpty() || newName.isEmpty() || newDescription.isEmpty() || newStock == null) {
                    Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
                } else {
                    // Verifica se o código já existe e não é o do próprio produto
                    if (newCode != originalProduct.codigo && dbHelper.isProductCodeExists(newCode)) {
                        Toast.makeText(this, "O código já está em uso por outro produto!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Atualiza o produto no banco de dados
                        val updatedProduct = Produto(
                            codigo = newCode,
                            nome = newName,
                            descricao = newDescription,
                            estoque = newStock
                        )

                        val success = dbHelper.updateProduct(originalProduct.codigo, updatedProduct)
                        if (success) {
                            Toast.makeText(this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Erro ao atualizar o produto!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            // Configuração do botão Cancelar
            binding.btnCancelUpdate.setOnClickListener {
                finish() // Fecha a tela sem salvar alterações
            }
        } else {
            Toast.makeText(this, "Código do produto não fornecido!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}



package com.example.imdmarket

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.data.IMDMarketDatabaseHelper
import com.example.imdmarket.databinding.ActivityListBinding


class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var dbHelper: IMDMarketDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        // Buscar os produtos do banco de dados
        val productList = dbHelper.getAllProducts()

        // Mapear os nomes dos produtos para exibição na lista
        val productNames = productList.map { it.nome }

        // Configurar o ArrayAdapter para o ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productNames)
        binding.lvProducts.adapter = adapter

        // Configurar clique nos itens da lista
        binding.lvProducts.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = productList[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("product_code", selectedProduct.codigo) // Passa o código do produto
            startActivity(intent)
        }

        // Mensagem caso a lista esteja vazia
        if (productNames.isEmpty()) {
            binding.tvProductListHeader.text = "Nenhum produto cadastrado!"
        }
    }
}

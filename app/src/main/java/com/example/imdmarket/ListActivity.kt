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
    private lateinit var adapter: ArrayAdapter<String>
    private var productList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = IMDMarketDatabaseHelper(this)

        // Configura o adapter para o ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productList)
        binding.lvProducts.adapter = adapter

        // Configurar clique nos itens da lista
        binding.lvProducts.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = dbHelper.getAllProducts()[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("product_code", selectedProduct.codigo) // Passa o código do produto
            startActivity(intent)
        }
    }
    //recarregar a lista de produtos quando a tela é reaberta
    override fun onResume() {
        super.onResume()

        // Recarregar a lista de produtos
        val products = dbHelper.getAllProducts()
        productList.clear()
        productList.addAll(products.map { it.nome })
        adapter.notifyDataSetChanged()

        // lógica do cabeçalho
        if (productList.isEmpty()) {
            binding.tvProductListHeader.text = "Nenhum produto cadastrado!"
        } else {
            binding.tvProductListHeader.text = "Lista de Produtos"
        }
    }
}

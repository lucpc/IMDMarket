package com.example.imdmarket

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.imdmarket.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val productList = mutableListOf<String>() // Lista mock de produtos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // atualizar com a lógica de dados depois
        productList.addAll(listOf("Produto 1", "Produto 2", "Produto 3"))

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, productList)
        binding.lvProducts.adapter = adapter
    }
}

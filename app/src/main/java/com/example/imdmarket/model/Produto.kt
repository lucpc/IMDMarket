package com.example.imdmarket.model

data class Produto(
    val codigo: String,        // Código único do produto
    val nome: String,          // Nome do produto
    val descricao: String,     // Descrição do produto
    val estoque: Int           // Quantidade em estoque
)
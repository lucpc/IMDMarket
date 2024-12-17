package com.example.imdmarket.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.imdmarket.model.Produto

class IMDMarketDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "IMDMarket.db"
        private const val DATABASE_VERSION = 1

        // Nome da tabela e colunas
        const val TABLE_PRODUCTS = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_STOCK = "stock"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela
        val createTableQuery = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_STOCK INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    // Função para adicionar um produto
    fun addProduct(product: Produto): Boolean {
        // Verifica se o código do produto já existe no banco
        if (isProductCodeExists(product.codigo)) {
            return false // Produto duplicado, não permite inserir
        }

        val db = writableDatabase // Abre o banco de dados no modo escrita
        val values = ContentValues().apply {
            put(COLUMN_ID, product.codigo)
            put(COLUMN_NAME, product.nome)
            put(COLUMN_DESCRIPTION, product.descricao)
            put(COLUMN_STOCK, product.estoque)
        }
        val result = db.insert(TABLE_PRODUCTS, null, values) // Tenta inserir o produto
        db.close() // Fecha o banco de dados
        return result != -1L // Retorna true se a inserção foi bem-sucedida
    }
    fun isProductCodeExists(code: String): Boolean {
        val db = readableDatabase // Abre o banco de dados no modo leitura
        val cursor = db.query(
            TABLE_PRODUCTS,          // Nome da tabela
            arrayOf(COLUMN_ID),      // Colunas a serem buscadas
            "$COLUMN_ID = ?",        // Condição de seleção (WHERE)
            arrayOf(code),           // Valores para a condição
            null,                    // groupBy
            null,                    // having
            null                     // orderBy
        )
        val exists = cursor.moveToFirst() // Verifica se a consulta retornou algum resultado
        cursor.close() // Fecha o cursor
        db.close() // Fecha o banco de dados
        return exists // Retorna true se o código já existir
    }


    // Função para obter todos os produtos
    fun getAllProducts(): List<Produto> {
        val productList = mutableListOf<Produto>()
        val db = readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val product = Produto(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK))
                )
                productList.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productList
    }

    // Função para deletar um produto
    fun deleteProduct(id: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_PRODUCTS, "$COLUMN_ID=?", arrayOf(id))
        db.close()
        return result > 0
    }

    // Função para atualizar um produto
    fun updateProduct(originalCode: String, updatedProduct: Produto): Boolean {
        val db = writableDatabase

        // Cria os valores para atualizar
        val values = ContentValues().apply {
            put(COLUMN_ID, updatedProduct.codigo)
            put(COLUMN_NAME, updatedProduct.nome)
            put(COLUMN_DESCRIPTION, updatedProduct.descricao)
            put(COLUMN_STOCK, updatedProduct.estoque)
        }

        // Atualiza o produto
        val result = db.update(
            TABLE_PRODUCTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(originalCode) // Usa o código original como critério
        )
        db.close()
        return result > 0
    }

}

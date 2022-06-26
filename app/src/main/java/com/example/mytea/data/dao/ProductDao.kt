package com.example.mytea.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytea.models.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM product ORDER BY ProductID ASC")
    fun getAllProduct(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE name LIKE :query ORDER BY ProductID ASC")
    fun searchProduct(query: String):LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE ProductID = :productId LIMIT 1")
    fun getProductbyId(productId: Int):LiveData<Product>
}
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
}
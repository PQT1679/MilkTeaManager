package com.example.mytea.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytea.models.Order


@Dao
interface OrderDao{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(order: Order):Long

    @Query("SELECT * FROM `Order` ORDER BY orderId ASC")
    fun getAllOrder() : LiveData<List<Order>>

    @Query("UPDATE `order` SET status = -1 WHERE orderId = :orderId")
    suspend fun cancelOrder(orderId: Int)

    @Query("UPDATE `order` SET status = 1 WHERE orderId = :orderId")
    suspend fun doneOrder(orderId: Int)
}
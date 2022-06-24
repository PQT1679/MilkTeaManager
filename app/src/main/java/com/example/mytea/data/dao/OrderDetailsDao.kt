package com.example.mytea.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytea.models.OrderDetails


@Dao
interface OrderDetailsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrderDetails(details: OrderDetails)

    @Query("SELECT * FROM `OrderDetails` ORDER BY detailID DESC")
    fun getAllDetails(): LiveData<List<OrderDetails>>

    @Query("SELECT * FROM `OrderDetails` WHERE orderId=:orderID ORDER BY detailID ASC")
    fun getDetailbyOrder(orderID : Int): LiveData<List<OrderDetails>>

    @Query("UPDATE `product` SET Stock=Stock-:quantity WHERE ProductID=:productId")
    suspend fun updateStock(productId:Int,quantity:Int)
}
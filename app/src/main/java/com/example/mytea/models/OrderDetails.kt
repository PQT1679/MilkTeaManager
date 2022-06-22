package com.example.mytea.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "OrderDetails")
data class OrderDetails(
    @Embedded
    val product:Product,
    var amount:Int,
    @Embedded
    var order: Order
){
    @PrimaryKey(autoGenerate = true)
    var detailID: Int = 0
}
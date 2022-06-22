package com.example.mytea.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mytea.models.Table
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Parcelize
@Entity(tableName = "Order")
data class Order(
    @Embedded
    val table: Table,
    val timeplaceorder: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")).toString(),
    var status:Int,
    var total:Double,
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var orderId:Int = 0
}
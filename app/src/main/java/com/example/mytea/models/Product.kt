package com.example.mytea.models

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
    val name:String,
    var Image: Bitmap,
    var Price:Double,
    var Stock: Int,
):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var ProductID:Int = 0
}
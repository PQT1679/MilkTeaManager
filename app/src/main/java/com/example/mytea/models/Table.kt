package com.example.mytea.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "table")
data class Table(
    @PrimaryKey(autoGenerate = true)
    var tableId: Int
): Parcelable
package com.example.mytea.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytea.models.Table

@Dao
interface TableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTable(table: Table)
    @Query("SELECT * FROM `table` ORDER BY tableId ASC")
    fun getAllTable() : LiveData<List<Table>>

    @Query("SELECT * FROM `table` WHERE tableId LIKE :searchQuery ORDER BY tableId ASC")
    fun searchTables(searchQuery: String): LiveData<List<Table>>
}
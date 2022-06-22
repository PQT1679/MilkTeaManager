package com.example.mytea.data.database

import androidx.lifecycle.LiveData
import com.example.mytea.data.dao.TableDao
import com.example.mytea.models.Table

class MyRepository(private val tableDao: TableDao ) {
    val readTableAllData: LiveData<List<Table>> = tableDao.getAllTable()
    suspend fun addTable(table: Table){
        tableDao.addTable(table)
    }
}
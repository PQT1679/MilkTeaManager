package com.example.mytea.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytea.data.dao.TableDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.data.database.MyRepository
import com.example.mytea.models.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TableViewModel(application: Application):AndroidViewModel(application) {
    private val readAllTables: LiveData<List<Table>>
    private val tableDao:TableDao
    init {
        tableDao = MyDatabase.getDatabase(application).tableDao()
        readAllTables= tableDao.getAllTable()
    }
    fun addTable(table: Table){
        viewModelScope.launch(Dispatchers.IO) {
            tableDao.addTable(table)
        }
    }
    fun getTablesData(): LiveData<List<Table>> = readAllTables
    fun searchTables(query: String):LiveData<List<Table>> =tableDao.searchTables("%$query%")
}
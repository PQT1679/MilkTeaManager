package com.example.mytea

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytea.data.dao.OrderDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.models.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application):AndroidViewModel(application) {
    private val _orders: LiveData<List<Order>>
    private val orderDao:OrderDao
    val orders:LiveData<List<Order>> get() = _orders
    init {
        orderDao=MyDatabase.getDatabase(application).orderDao()
        _orders=orderDao.getAllOrder()
    }


    fun cancelOrder(orderid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            orderDao.cancelOrder(orderid)
        }

    }

    fun doneOrder(orderid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderDao.doneOrder(orderid)
        }

    }
}
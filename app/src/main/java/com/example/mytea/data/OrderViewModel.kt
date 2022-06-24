package com.example.mytea.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytea.data.dao.OrderDao
import com.example.mytea.data.dao.OrderDetailsDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.models.Order
import com.example.mytea.models.OrderDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application):AndroidViewModel(application) {
    private val _orders: LiveData<List<Order>>
    private val orderDao:OrderDao
    private val orderDetailsDao: OrderDetailsDao

    val orders:LiveData<List<Order>> get() = _orders
    init {
        orderDao=MyDatabase.getDatabase(application).orderDao()
        _orders=orderDao.getAllOrder()
        orderDetailsDao = MyDatabase.getDatabase(application).orderDetailsDao()
    }


    fun cancelOrder(orderid: Int){
        viewModelScope.launch(Dispatchers.IO) {
            orderDao.cancelOrder(orderid)
        }

    }

    fun doneOrder(orderId: Int, details: MutableList<OrderDetails>) {
        viewModelScope.launch(Dispatchers.IO) {
            orderDao.doneOrder(orderId)
            if (details.size > 0) {
                for (item in details){
                    orderDetailsDao.updateStock(item.product.ProductID,item.amount)
                }
            }

        }
    }
}
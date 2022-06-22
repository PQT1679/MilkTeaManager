package com.example.mytea.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytea.data.dao.OrderDetailsDao
import com.example.mytea.data.dao.ProductDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.models.Order
import com.example.mytea.models.OrderDetails
import com.example.mytea.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderDetailsViewModel(application: Application):AndroidViewModel(application) {
    private val _orderDetails: LiveData<List<OrderDetails>>
    private val orderDetailsDao: OrderDetailsDao
    val orderDetails get() = _orderDetails
    init {
        orderDetailsDao = MyDatabase.getDatabase(application).orderDetailsDao()
        _orderDetails = orderDetailsDao.getAllDetails()
    }
    fun addDetails(details: OrderDetails){
        viewModelScope.launch(Dispatchers.IO) {
            orderDetailsDao.addOrderDetails(details)
        }
    }
    fun getAllProduct(): LiveData<List<OrderDetails>> =orderDetails
    fun getDetailbyOrder(orderid: Int): LiveData<List<OrderDetails>>  =orderDetailsDao.getDetailbyOrder(orderid)
}
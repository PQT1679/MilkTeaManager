package com.example.mytea.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mytea.data.dao.OrderDao
import com.example.mytea.data.dao.OrderDetailsDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.models.Order
import com.example.mytea.models.OrderDetails
import com.example.mytea.models.Product
import com.example.mytea.models.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedOrderViewModel(application: Application): AndroidViewModel(application) {
    private var _table = MutableLiveData<Table>()
    private var _order = MutableLiveData<Order>()
    private var _orderdetails = MutableLiveData<MutableList<OrderDetails>>()
    private val orderDetailsDao:OrderDetailsDao
    private val orderDao: OrderDao
    init {
        orderDetailsDao = MyDatabase.getDatabase(application).orderDetailsDao()
        orderDao = MyDatabase.getDatabase(application).orderDao()
    }
    val orderDetails: LiveData<MutableList<OrderDetails>> get() = _orderdetails
    val table:LiveData<Table> get()  = _table
    val order:LiveData<Order> get() =_order

    fun setData(t: Table){
        Log.v("SharedOrderViewModel","Table: $t")
        if (t.tableId!=-1){
            _table.value=t
            _order.value=Order(t, status = 0, total = 0.0)
            _orderdetails.value= mutableListOf<OrderDetails>()
        }
    }
    private fun updateTotal(){
        var result = 0.0

        _orderdetails.value?.forEach { item ->
            result+=(item.product.Price*item.amount)
        }
        _order.value?.total = result
    }
    fun addOrderDetails(product: Product,quantity : Int){
        var details: MutableList<OrderDetails>? = _orderdetails.value
        var isExist = false
        if (details != null) {
            for (item in details){
//                Log.v("SharedViewModel","p1:${product.toString()} vs p2:${item.product.toString()} is equals:${product.name == item.product.name} ")
                if (product.name == item.product.name){
                    item.amount =item.amount+quantity
                    isExist=true
                }
            }
            if (!isExist){
                details.add(OrderDetails(product,quantity,_order.value!!))
            }
        }
        else{
            details= mutableListOf(OrderDetails(product,quantity,_order.value!!))
        }
        _orderdetails.value = details!!
        updateTotal()
    }

    @Synchronized
    fun placeOrder(){
        viewModelScope.launch(Dispatchers.IO) {
            val orderid= orderDao.addOrder(_order.value!!).toInt()
            Log.v("Order","${orderid.toString()}")
            for (item in _orderdetails.value!!){
                item.order.orderId=orderid
                orderDetailsDao.addOrderDetails(item)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
    fun wipedata(){
        onCleared()
    }
}
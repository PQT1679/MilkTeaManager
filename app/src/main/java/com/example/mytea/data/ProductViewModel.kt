package com.example.mytea.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytea.data.dao.ProductDao
import com.example.mytea.data.database.MyDatabase
import com.example.mytea.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application):AndroidViewModel(application) {
    private val Products: LiveData<List<Product>>
    private val productDao: ProductDao
    init {
       productDao = MyDatabase.getDatabase(application).productDao()
        Products = productDao.getAllProduct()
    }
    fun addProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            productDao.addProduct(product)
        }
    }
    fun getAllProduct():LiveData<List<Product>> =Products
    fun updateProduct(product: Product){
        viewModelScope.launch {
            productDao.updateProduct(product)
        }
    }
}
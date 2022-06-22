package com.example.mytea.data.database

import android.content.Context
import androidx.room.*
import com.example.mytea.data.Converter
import com.example.mytea.data.dao.OrderDao
import com.example.mytea.data.dao.OrderDetailsDao
import com.example.mytea.data.dao.ProductDao
import com.example.mytea.data.dao.TableDao
import com.example.mytea.models.Order
import com.example.mytea.models.OrderDetails
import com.example.mytea.models.Product
import com.example.mytea.models.Table

@Database(entities = [Table::class,Order::class,Product::class,OrderDetails::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun tableDao():TableDao
    abstract fun productDao(): ProductDao
    abstract fun orderDetailsDao(): OrderDetailsDao
    abstract fun orderDao(): OrderDao
    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun  getDatabase(context: Context):MyDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,MyDatabase::class.java,"my_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

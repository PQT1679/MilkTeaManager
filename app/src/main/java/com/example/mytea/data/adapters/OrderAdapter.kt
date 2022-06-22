package com.example.mytea.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytea.R
import com.example.mytea.fragments.OrderManagerDirections
import com.example.mytea.models.Order

class OrderAdapter:RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var orders = emptyList<Order>()
    class OrderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_layout,parent,false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orders[position]
        holder.itemView.findViewById<TextView>(R.id.order_item_table).text=holder.itemView.findViewById<TextView>(R.id.order_item_table).text.toString() +currentItem.table.tableId.toString()
        holder.itemView.findViewById<TextView>(R.id.order_time_placeorder).text=currentItem.timeplaceorder
        holder.itemView.findViewById<TextView>(R.id.order_totalmoney).text = holder.itemView.findViewById<TextView>(R.id.order_totalmoney).text.toString()+ currentItem.total.toString() +"$"
        holder.itemView.findViewById<TextView>(R.id.order_orderid).text = holder.itemView.findViewById<TextView>(R.id.order_orderid).text.toString() + currentItem.orderId
        holder.itemView.findViewById<TextView>(R.id.order_status).text =holder.itemView.findViewById<TextView>(R.id.order_status).text.toString()+ when(currentItem.status){
            0->"Chờ Xử Lí"
            1->"Đã Hoàn Tất"
            else->"Đã Bị Hủy"
        }
        holder.itemView.findViewById<Button>(R.id.order_btn_show_orderdetails).setOnClickListener {
            val action = OrderManagerDirections.actionOrderManagerToOrderInfo(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount()=orders.size

    fun setData(data:List<Order>){
        orders=data
        notifyDataSetChanged()
    }
}
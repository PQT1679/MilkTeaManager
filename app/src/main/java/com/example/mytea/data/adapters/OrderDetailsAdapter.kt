package com.example.mytea.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytea.R
import com.example.mytea.models.OrderDetails

class OrderDetailsAdapter:RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailViewHolder>() {
    private var orderDetails = emptyList<OrderDetails>()

    class OrderDetailViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        return OrderDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.orderdetail_layout,parent,false))
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val currentItem = orderDetails[position]
        holder.itemView.findViewById<TextView>(R.id.detail_item_name).text = currentItem.product.name
        holder.itemView.findViewById<TextView>(R.id.detail_item_price).text ="Giá: "+ currentItem.product.Price.toString() +"$"
        holder.itemView.findViewById<TextView>(R.id.detail_item_money).text ="Thành Tiền: "+ (currentItem.product.Price*currentItem.amount).toString()
        holder.itemView.findViewById<ImageView>(R.id.detail_item_img).setImageBitmap(currentItem.product.Image)
        holder.itemView.findViewById<TextView>(R.id.detail_item_quantity).text = "Số Lượng: " +currentItem.amount.toString()
    }

    override fun getItemCount()=orderDetails.size

    fun setData(details: MutableList<OrderDetails>){
        orderDetails = details
        notifyDataSetChanged()
    }
}
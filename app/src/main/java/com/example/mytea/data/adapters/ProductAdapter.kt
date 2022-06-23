package com.example.mytea.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytea.fragments.ProductManagerDirections
import com.example.mytea.R
import com.example.mytea.fragments.FragmentOrderDetailsDirections
import com.example.mytea.models.Product


class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList = emptyList<Product>()
    private var action  = R.id.action_productManager_to_updateProduct

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val product_img :ImageView
        val product_name : TextView
        val product_price : TextView
        val product_quantity : TextView
        val product : View
        init {
            product_img = itemView.findViewById(R.id.item_image)
            product_name=itemView.findViewById(R.id.item_name)
            product_price=itemView.findViewById(R.id.item_price)
            product_quantity = itemView.findViewById<TextView>(R.id.item_quantity)
            product = itemView.findViewById<View>(R.id.product_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.product_img.setImageBitmap(currentItem.Image)
        holder.product_name.text=currentItem.name
        holder.product_price.text="Giá: "+currentItem.Price.toString()+"$"
        holder.product_quantity.text="Hiện Có: " +currentItem.Stock.toString()
        holder.product.setOnClickListener {
            if(action==R.id.action_orderDetails_to_chooseProduct){
                val newaction= FragmentOrderDetailsDirections.actionOrderDetailsToChooseProduct(currentItem)
                holder.itemView.findNavController().navigate(newaction)
            }
            else{
                val newaction = ProductManagerDirections.actionProductManagerToUpdateProduct(currentItem)
                holder.itemView.findNavController().navigate(newaction)
            }

        }
    }
    fun setaction(action: Int){
        this.action = action
    }
    override fun getItemCount(): Int =productList.size

    fun setData(products: List<Product>){
        productList=products
        notifyDataSetChanged()
    }
}
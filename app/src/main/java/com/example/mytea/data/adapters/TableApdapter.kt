package com.example.mytea.data.adapters

import android.app.DirectAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytea.MainActivity
import com.example.mytea.R
import com.example.mytea.databinding.TableLayoutBinding
import com.example.mytea.fragments.Tables
import com.example.mytea.fragments.TablesDirections
import com.example.mytea.models.Table

class TableApdapter:RecyclerView.Adapter<TableApdapter.TableViewHolder>()  {
    private var tablelist = emptyList<Table>()
    class TableViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tableId:TextView
        init {
            tableId = itemView.findViewById(R.id.table_id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        return TableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.table_layout,parent,false))
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val currentItem = tablelist[position]
        holder.tableId.text = currentItem.tableId.toString()
        holder.tableId.setOnClickListener {
            val action = TablesDirections.actionTablesToPlaceOrder(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun onBindViewHolder(
        holder: TableViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }


    override fun getItemCount(): Int =tablelist.size


    fun setData(tables: List<Table>) {
        this.tablelist=tables
        notifyDataSetChanged()
    }

}
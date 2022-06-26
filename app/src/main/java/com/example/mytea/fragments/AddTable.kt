package com.example.mytea.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mytea.R
import com.example.mytea.data.TableViewModel
import com.example.mytea.databinding.FragmentAddTableBinding
import com.example.mytea.models.Table

class AddTable : Fragment() {
    private lateinit var binding: FragmentAddTableBinding
    private lateinit var viewModel: TableViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTableBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(TableViewModel::class.java)

        binding.button.setOnClickListener {
            insertData()
        }
        activity?.title="Thêm Bàn"
        return binding.root
    }

    private fun insertData() {
        val tableId = binding.tableid.text
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Xác Nhận"){_,_->
            if (!checkEmptyInput(tableId)){
                val table = Table(Integer.parseInt(tableId.toString()))
                viewModel.addTable(table)
                Toast.makeText(requireContext(),"Thành Công Thêm Bàn $tableId ",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireContext(),"Vui Lòng Nhập Số Bàn!",Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_addTable_to_tables)
        }
        builder.setNegativeButton("Hủy Bỏ"){_,_ ->}
        builder.setTitle("Xác Nhận Thêm Bàn")
        builder.setMessage("Thêm Bàn Số $tableId")
        builder.create().show()
    }
    private fun checkEmptyInput(tableId:Editable):Boolean{
        return tableId.isEmpty()
    }
}
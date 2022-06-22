package com.example.mytea.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
        if (!checkEmptyInput(tableId)){
            val table = Table(Integer.parseInt(tableId.toString()))
            viewModel.addTable(table)
            Toast.makeText(requireContext(),"Added table Id ${tableId}",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"Please Fill all Field",Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkEmptyInput(tableId:Editable):Boolean{
        return tableId.isEmpty()
    }
}
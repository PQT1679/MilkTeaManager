package com.example.mytea

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.data.ProductViewModel
import com.example.mytea.data.adapters.ProductAdapter
import com.example.mytea.databinding.FragmentProductManagerBinding


class ProductManager : Fragment() {

    private lateinit var binding: FragmentProductManagerBinding
    private lateinit var viewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductManagerBinding.inflate(layoutInflater)
        binding.addProduct.setOnClickListener{
            findNavController().navigate(R.id.action_productManager_to_addProduct)
        }



        val adapter = ProductAdapter()
        val recycleview = binding.viewProducts
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.getAllProduct().observe(viewLifecycleOwner
        ) { products -> adapter.setData(products) }


        return binding.root
    }

}
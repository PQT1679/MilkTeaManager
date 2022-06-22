package com.example.mytea.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.R
import com.example.mytea.data.ProductViewModel
import com.example.mytea.data.adapters.ProductAdapter
import com.example.mytea.databinding.FragmentProductManagerBinding


class ProductManager : Fragment(),SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentProductManagerBinding
    private lateinit var viewModel: ProductViewModel
    val adapter = ProductAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductManagerBinding.inflate(layoutInflater)
        binding.addProduct.setOnClickListener{
            findNavController().navigate(R.id.action_productManager_to_addProduct)
        }




        val recycleview = binding.viewProducts
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.getAllProduct().observe(viewLifecycleOwner
        ) { products -> adapter.setData(products) }
        activity?.title="Quản Lí Sản Phẩm"
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tables_menu,menu)
        val search = menu?.findItem(R.id.table_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled= true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            searchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchData(query)
        }
        return true
    }
    private fun searchData(query: String) {
        viewModel.searchProduct(query).observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
}
package com.example.mytea.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytea.R
import com.example.mytea.data.OrderDetailsViewModel
import com.example.mytea.data.ProductViewModel
import com.example.mytea.data.SharedOrderViewModel
import com.example.mytea.data.TableViewModel
import com.example.mytea.data.adapters.ProductAdapter
import com.example.mytea.data.adapters.TableApdapter
import com.example.mytea.databinding.FragmentOrderDetailsBinding
import com.example.mytea.databinding.FragmentTablesBinding


class FragmentOrderDetails : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var viewModel: ProductViewModel
    val adapter = ProductAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        adapter.setaction(R.id.action_orderDetails_to_chooseProduct)
        val recycleview = binding.productToChoose
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.getAllProduct().observe(viewLifecycleOwner, Observer { tables->
            adapter.setData(tables)
        })
        setHasOptionsMenu(true)
        activity?.title="Chọn Sản Phẩm"
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

    private fun searchData(query: String) {
        viewModel.searchProduct(query).observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchData(query)
        }
        return true
    }

}
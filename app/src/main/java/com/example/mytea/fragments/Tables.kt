package com.example.mytea.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.R
import com.example.mytea.data.TableViewModel
import com.example.mytea.data.adapters.TableApdapter
import com.example.mytea.databinding.FragmentTablesBinding


class Tables : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var tableViewModel : TableViewModel
    private lateinit var binding : FragmentTablesBinding
    private val adapter = TableApdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTablesBinding.inflate(layoutInflater)
        binding.addTable.setOnClickListener{
            findNavController().navigate(R.id.action_tables_to_addTable)
        }
        setHasOptionsMenu(true)
//        val adapter = TableApdapter()
        val recycleview = binding.tableRecycle
        recycleview.adapter = adapter
        recycleview.layoutManager = GridLayoutManager(requireContext(),2)

        tableViewModel = ViewModelProvider(this).get(TableViewModel::class.java)
        tableViewModel.getTablesData().observe(viewLifecycleOwner, Observer { tables->
            adapter.setData(tables)
        })
        activity?.title="Quản Lý Quán"
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_tables_to_dashBoard)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
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
            searchDatabased(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            searchDatabased(query)
        }
        return true
    }
    private fun searchDatabased(query: String){
        tableViewModel.searchTables(query).observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
}
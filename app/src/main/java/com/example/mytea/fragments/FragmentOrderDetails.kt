package com.example.mytea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


class FragmentOrderDetails : Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var viewModel: ProductViewModel
    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)

        val adapter = ProductAdapter()
        adapter.setaction(R.id.action_orderDetails_to_chooseProduct)
        val recycleview = binding.productToChoose
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.getAllProduct().observe(viewLifecycleOwner, Observer { tables->
            adapter.setData(tables)
        })

        return binding.root
    }

}
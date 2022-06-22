package com.example.mytea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.data.OrderViewModel
import com.example.mytea.data.adapters.OrderAdapter
import com.example.mytea.databinding.FragmentOrderManagerBinding


class OrderManager : Fragment() {
    private lateinit var binding:FragmentOrderManagerBinding
    private lateinit var viewModel: OrderViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentOrderManagerBinding.inflate(layoutInflater)


        val adapter = OrderAdapter()
        val recyclerView = binding.orderRecyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        viewModel= ViewModelProvider(this)[OrderViewModel::class.java]
        viewModel.orders.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
        activity?.title="Quản Lí Đơn Hàng"
        return binding.root
    }
}
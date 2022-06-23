package com.example.mytea.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.R
import com.example.mytea.data.ProductViewModel
import com.example.mytea.data.SharedOrderViewModel
import com.example.mytea.data.adapters.OrderDetailsAdapter
import com.example.mytea.databinding.FragmentPlaceOrderBinding
import com.example.mytea.models.OrderDetails
import java.lang.Exception


class PlaceOrder : Fragment() {
    private lateinit var binding: FragmentPlaceOrderBinding
    val args: PlaceOrderArgs by navArgs()
    private val sharedOrderViewModel:SharedOrderViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("PlaceOrder","onCreateView")
        binding = FragmentPlaceOrderBinding.inflate(layoutInflater)
        binding.btnPlaceOrder.isVisible=false
        sharedOrderViewModel.setData(args.currentTable)
        binding.addOrderDetails.setOnClickListener {
                val currenttable = args.currentTable
                    val action = PlaceOrderDirections.actionPlaceOrderToOrderDetails(currenttable)
            findNavController().navigate(action)
        }

        val adapter = OrderDetailsAdapter()
        val recyclerView = binding.recyclerViewDetails
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedOrderViewModel.orderDetails.observe(viewLifecycleOwner) {
            if (it.size >= 1) {
                binding.orderTotal.text = binding.orderTotal.text.toString()+ sharedOrderViewModel.order.value?.total.toString() + "$"
                binding.orderTotal.isVisible = true
                adapter.setData(sharedOrderViewModel.orderDetails.value!!)
                binding.btnPlaceOrder.isVisible = true
                binding.btnPlaceOrder.setOnClickListener {
                    placeOrder()
                }
                binding.placeOrderEmptyCart.isVisible=false
            } else {
                binding.orderTotal.isVisible = false
                binding.btnPlaceOrder.isVisible = false
                binding.placeOrderEmptyCart.isVisible=true
            }
        }
        activity?.title="Thông Tin Đơn Hàng"
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("PlaceOrder","onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("PlaceOrder","onViewCreated")
        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                sharedOrderViewModel.wipedata()
                findNavController().navigate(R.id.action_placeOrder_to_tables)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    private fun placeOrder(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Xác Nhận"){_,_->
            sharedOrderViewModel.placeOrder()
            Toast.makeText(requireContext(),"Successfully Place Order",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_placeOrder_to_tables)
        }
        builder.setNegativeButton("Hủy Bỏ"){_,_ ->}
        builder.setTitle("Xác Nhận Đặt Hàng")
        builder.setMessage("Tạo Đơn Hàng Với Tổng Tiền: ${sharedOrderViewModel.order.value?.total}$")
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        Log.v("PlaceOrder","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("PlaceOrder","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v("PlaceOrder","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v("PlaceOrder","onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v("PlaceOrder","onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("PlaceOrder","onDestroy")
    }
}
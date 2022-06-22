package com.example.mytea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytea.data.OrderViewModel
import com.example.mytea.R
import com.example.mytea.data.OrderDetailsViewModel
import com.example.mytea.data.adapters.OrderDetailsAdapter
import com.example.mytea.databinding.FragmentOrderInfoBinding
import com.example.mytea.models.OrderDetails

class OrderInfo : Fragment() {
    private lateinit var binding: FragmentOrderInfoBinding
    private lateinit var detailViewModel: OrderDetailsViewModel
    private lateinit var viewModel: OrderViewModel
    val args: OrderInfoArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderInfoBinding.inflate(layoutInflater)
        detailViewModel = ViewModelProvider(this)[OrderDetailsViewModel::class.java]

        viewModel=ViewModelProvider(this)[OrderViewModel::class.java]

        val adapter = OrderDetailsAdapter()
        val recyclerView = binding.orderinfoRecyclerview
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        detailViewModel.getDetailbyOrder(args.currentOrder.orderId).observe(viewLifecycleOwner){
            adapter.setData(it as MutableList<OrderDetails>)
        }
        binding.orderinfoTotal.text = "Tổng Tiền: "+args.currentOrder.total+"$"
        if (args.currentOrder.status !=0){
            binding.orderinfoBtnCanceled.visibility= View.GONE
            binding.orderinfoBtnConfirm.visibility= View.GONE
            binding.orderinfoStatus.visibility = View.VISIBLE
            binding.orderinfoStatus.text = when(args.currentOrder.status){
                1->"Đơn Hàng Đã Hoàn Tất"
                else->"Đơn Hàng Đã Bị Hủy"
            }
        }
        else{
            binding.orderinfoBtnCanceled.setOnClickListener {
                Toast.makeText(requireContext(),"BT1",Toast.LENGTH_LONG).show()
                viewModel.cancelOrder(args.currentOrder.orderId)
                findNavController().navigate(R.id.action_orderInfo_to_orderManager)
            }
            binding.orderinfoBtnConfirm.setOnClickListener {
                Toast.makeText(requireContext(),"BT2",Toast.LENGTH_LONG).show()
                viewModel.doneOrder(args.currentOrder.orderId)
                findNavController().navigate(R.id.action_orderInfo_to_orderManager)
            }
        }
        activity?.title="Thông Tin Đơn Hàng"
        return binding.root
    }

}
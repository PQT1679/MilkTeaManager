package com.example.mytea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
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
        detailViewModel.getDetailbyOrder(args.currentOrder.orderId).observe(viewLifecycleOwner){details->
            adapter.setData(details as MutableList<OrderDetails>)
            if(details.isNotEmpty()){
                binding.orderinfoBtnCanceled.setOnClickListener {
                    cancelOrder()
                }
                binding.orderinfoBtnConfirm.setOnClickListener {
                    doneOrder(details)
                }
            }
        }
        binding.orderinfoTotal.text = "T???ng Ti???n: "+args.currentOrder.total+"$"
        if (args.currentOrder.status !=0){
            binding.orderinfoBtnCanceled.visibility= View.GONE
            binding.orderinfoBtnConfirm.visibility= View.GONE
            binding.orderinfoStatus.visibility = View.VISIBLE
            binding.orderinfoStatus.text = when(args.currentOrder.status){
                1->"????n H??ng ???? Ho??n T???t"
                else->"????n H??ng ???? B??? H???y"
            }
        }
        activity?.title="Th??ng Tin ????n H??ng"
        return binding.root
    }
    private fun doneOrder(details: MutableList<OrderDetails>) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("X??c Nh???n"){_,_->
            Toast.makeText(requireContext(),"Successfully",Toast.LENGTH_LONG).show()
            viewModel.doneOrder(args.currentOrder.orderId,details)
            findNavController().navigate(R.id.action_orderInfo_to_orderManager)
        }
        builder.setNegativeButton("H???y B???"){_,_ ->}
        builder.setTitle("X??c Nh???n Ho??n T???t")
        builder.setMessage("X??c Nh???n Ho??n T???t ????n H??ng #${args.currentOrder.orderId} ")
        builder.create().show()
    }
    private fun cancelOrder(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("X??c Nh???n"){_,_->
            Toast.makeText(requireContext(),"Order #${args.currentOrder.orderId} Canceled Successfully",Toast.LENGTH_LONG).show()
            viewModel.cancelOrder(args.currentOrder.orderId)
            findNavController().navigate(R.id.action_orderInfo_to_orderManager)
        }
        builder.setNegativeButton("H???y B???"){_,_ ->}
        builder.setTitle("H???y ????n H??ng #${args.currentOrder.orderId} ")
        builder.setMessage("X??c Nh???n H???y ????n H??ng #${args.currentOrder.orderId} ")
        builder.create().show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_orderInfo_to_orderManager)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }
}
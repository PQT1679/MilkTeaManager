package com.example.mytea.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytea.R
import com.example.mytea.databinding.FragmentDashBoardBinding


class DashBoard : Fragment() {
    private lateinit var binding: FragmentDashBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater)

        binding.productManage.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_productManager)
        }
        binding.store.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_tables)
        }
        binding.orderManage.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_orderManager)
        }
        binding.dashboardAbout.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoard_to_about)
        }
        return binding.root
    }

}
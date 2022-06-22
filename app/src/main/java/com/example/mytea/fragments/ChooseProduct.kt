package com.example.mytea.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytea.R
import com.example.mytea.data.SharedOrderViewModel
import com.example.mytea.databinding.FragmentChooseProductBinding
import com.example.mytea.models.Table
import kotlinx.coroutines.delay


class ChooseProduct : Fragment() {
    private lateinit var binding: FragmentChooseProductBinding
    val args: ChooseProductArgs by navArgs()
    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChooseProductBinding.inflate(layoutInflater)
        binding.chooseProductImg.setImageBitmap(args.currentProduct.Image)
        binding.chooseProductPrice.text =binding.chooseProductPrice.text.toString()+args.currentProduct.Price.toString()+"$"
        binding.chooseProductName.text = args.currentProduct.name
        binding.chooseProductStock.text=binding.chooseProductStock.text.toString()+ args.currentProduct.Stock.toString()
        binding.chooseProductBtnMinus.setOnClickListener {
            val quantity=binding.textView.text.toString().toInt()
            if (quantity>1){
                binding.textView.text = (quantity-1).toString()
            }
        }
        binding.chooseProductBtnIn.setOnClickListener {
            binding.textView.text = (binding.textView.text.toString().toInt()+1).toString()
        }
        binding.addToOrder.setOnClickListener {
            sharedOrderViewModel.addOrderDetails(args.currentProduct,binding.textView.text.toString().toInt())
            val action = ChooseProductDirections.actionChooseProductToPlaceOrder(Table(-1))
            findNavController().navigate(action)
        }
        return binding.root
    }

}
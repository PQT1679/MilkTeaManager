package com.example.mytea.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytea.R
import com.example.mytea.data.ProductViewModel
import com.example.mytea.data.SharedOrderViewModel
import com.example.mytea.databinding.FragmentChooseProductBinding
import com.example.mytea.models.Table
import kotlinx.coroutines.delay


class ChooseProduct : Fragment() {
    private lateinit var binding: FragmentChooseProductBinding
    private lateinit var viewModel:ProductViewModel
    val args: ChooseProductArgs by navArgs()
    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChooseProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel.getProductbyId(args.currentProduct).observe(viewLifecycleOwner){product->
            binding.chooseProductImg.setImageBitmap(product.Image)
            binding.chooseProductPrice.text =binding.chooseProductPrice.text.toString()+product.Price.toString()+"$"
            binding.chooseProductName.text = product.name
            binding.chooseProductStock.text=binding.chooseProductStock.text.toString()+ product.Stock.toString()
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
                sharedOrderViewModel.addOrderDetails(product,binding.textView.text.toString().toInt())
                val action = ChooseProductDirections.actionChooseProductToPlaceOrder(Table(-1))
                findNavController().navigate(action)
            }
        }
        activity?.title="Thêm Sản Phẩm"
        return binding.root
    }

}
package com.example.mytea.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.mytea.data.ProductViewModel
import com.example.mytea.databinding.FragmentUpdateProductBinding
import com.example.mytea.models.Product


class UpdateProduct : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentUpdateProductBinding
    private val args: UpdateProductArgs by navArgs()
    private var productImg : Bitmap? =null
    private val PICK_IMG = 95

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        binding = FragmentUpdateProductBinding.inflate(layoutInflater)
        binding.editProductName.setText(args.currentProduct.name)
        binding.editImageProduct.setImageBitmap(args.currentProduct.Image)
        binding.editProductPrice.setText(args.currentProduct.Price.toString())
        binding.editProductQuantityText.setText(args.currentProduct.Stock.toString())
        binding.btnEditProductImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/png"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,PICK_IMG)
        }
        binding.btnEditProduct.setOnClickListener {
            updateProduct()
        }
        activity?.title="Cập Nhật Sản Phẩm"
        return binding.root
    }

    private fun updateProduct() {
        val productname = binding.editProductName.text.toString()
        val productimg =  productImg
        val productprice = binding.editProductPrice.text
        val quantity = binding.editProductQuantityText.text
        if (!productname.isEmpty() && !productprice.isEmpty() && !quantity.isEmpty() ){

            val product = Product(productname,productimg!!,productprice.toString().toDouble() ,Integer.parseInt(quantity.toString()) )
            productViewModel.updateProduct(product)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMG && resultCode== Activity.RESULT_OK && data!=null && data.data !=null ){
                val imagePath = data.data
                productImg = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(imagePath!!))
                binding.editImageProduct.setImageBitmap(productImg)
            }
        }
        catch (e: Exception){
            Toast.makeText(requireContext(),e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
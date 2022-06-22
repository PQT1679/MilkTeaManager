package com.example.mytea.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytea.data.ProductViewModel
import com.example.mytea.databinding.FragmentAddProductBinding
import com.example.mytea.models.Product


class AddProduct : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private lateinit var viewModel :ProductViewModel
    private lateinit var bitmap: Bitmap
    private val PICK_IMG = 99
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        binding.btnAddProductImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/png"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,PICK_IMG)
        }

        binding.btnAddProduct.setOnClickListener {
            insertProduct()
        }
        activity?.title="Thêm Sản Phẩm"
        return binding.root
    }

    private fun insertProduct() {
        val productname = binding.productName.text.toString()
        val productimg:Bitmap =  bitmap
        val productprice = binding.productPrice.text
        val quantity = binding.productQuantityText.text
        Toast.makeText(requireContext(),"${productname.isEmpty()} hihi ${productprice.isEmpty()} hihi ${quantity.isEmpty()} ",Toast.LENGTH_SHORT).show()
        if (!productname.isEmpty() && !productprice.isEmpty() && !quantity.isEmpty() ){

            val product = Product(productname,productimg,productprice.toString().toDouble() ,Integer.parseInt(quantity.toString())  )
//            Toast.makeText(requireContext(),"${product.toString()}",Toast.LENGTH_SHORT).show()
            viewModel.addProduct(product)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMG && resultCode== RESULT_OK && data!=null && data.data !=null ){
                val imagePath = data.data
                bitmap = BitmapFactory.decodeStream(context?.contentResolver?.openInputStream(imagePath!!))
                binding.imageProduct.setImageBitmap(bitmap)
            }
        }
        catch (e: Exception){
            Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
        }
    }
}
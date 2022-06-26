package com.example.mytea.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytea.R
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
    ): View {
        binding = FragmentUpdateProductBinding.inflate(layoutInflater)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productViewModel.getProductbyId(args.currentProduct).observe(viewLifecycleOwner){product->
            binding.editProductName.setText(product.name)
            binding.editImageProduct.setImageBitmap(product.Image)
            binding.editProductPrice.setText(product.Price.toString())
            binding.editProductQuantityText.setText(product.Stock.toString())
            binding.btnEditProductImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/png"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,PICK_IMG)
            }
            binding.btnEditProduct.setOnClickListener {
                updateProduct(product)
            }
        }

        activity?.title="Cập Nhật Sản Phẩm"
        return binding.root
    }

    private fun updateProduct(product: Product) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Xác Nhận"){_,_->
            val productname = binding.editProductName.text.toString()
            if (productImg==null){
                productImg=product.Image
            }
            val productprice = binding.editProductPrice.text
            val quantity = binding.editProductQuantityText.text
            if (!productname.isEmpty() && !productprice.isEmpty() && !quantity.isEmpty() ){

                val p = Product(productname,productImg!!,productprice.toString().toDouble() ,Integer.parseInt(quantity.toString()) )
                Log.v("ProductViewModel","$p")
                Log.v("ProductViewModel","${p.ProductID}")
                p.ProductID = product.ProductID
                Log.v("ProductViewModel","${product.ProductID}")
                Log.v("ProductViewModel","${p.ProductID}")
                productViewModel.updateProduct(p)
            }
            Toast.makeText(requireContext(),"Cập Nhật Sản Phẩm ${product.name} Thành Công",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateProduct_to_productManager)
        }
        builder.setNegativeButton("Hủy Bỏ"){_,_ ->}
        builder.setTitle("Cập Nhật Sản Phẩm")
        builder.setMessage("Xác Nhận Cập Nhật Sản Phẩm ${product.name}!")
        builder.create().show()

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
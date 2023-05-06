package com.example.comparapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.comparapp.data.Product
import com.example.comparapp.databinding.FragmentProductExtraInformationBinding


class ProductExtraInformationFragment : Fragment() {

    private lateinit var binding: FragmentProductExtraInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductExtraInformationBinding.inflate(inflater)
        updateProduct()
        return binding.root
    }

    private fun updateProduct() {
        val product = requireArguments().get("product") as Product?

        if (product != null) {
            binding.productName.text = product.name
            Glide.with(requireContext()).load(product.urlPhoto).into(binding.productImage)
            binding.ingredientsContent.text = product.ingredients

            val energeticValue = convertToString(product.nutritionalValue!!["energeticValue"]!!)
            val fats = convertToString(product.nutritionalValue!!["fats"]!!)
            val carbohydrates = convertToString(product.nutritionalValue!!["carbohydrates"]!!)
            val proteins = convertToString(product.nutritionalValue!!["proteins"]!!)

            binding.energeticValue.text = binding.energeticValue.text.toString() + energeticValue
            binding.fats.text = binding.fats.text.toString() + fats
            binding.carbohydrates.text = binding.carbohydrates.text.toString() + carbohydrates
            binding.proteins.text = binding.proteins.text.toString() + proteins
        }
    }

    private fun convertToString(number: Double): String {
        return " " + number.toString() + "g"
    }
}
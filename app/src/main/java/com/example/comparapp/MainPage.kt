package com.example.comparapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.comparapp.adapter.CardViewAdapter
import com.example.comparapp.data.Product
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentMainPageBinding
import com.example.comparapp.databinding.FragmentSearchBinding
import com.example.comparapp.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainPage : Fragment() {


    private lateinit var _binding: FragmentMainPageBinding
    private val productViewModel: ProductViewModel by viewModels()
    private var products: List<Product> = ArrayList()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoria1.setOnClickListener { addNavigation(binding.categoria1, "Cereales") }
        binding.categoria2.setOnClickListener { addNavigation(binding.categoria2, "Verduras") }
        binding.categoria3.setOnClickListener { addNavigation(binding.categoria3, "Lácteos") }
        binding.categoria4.setOnClickListener { addNavigation(binding.categoria4, "Fruta") }
        binding.categoria5.setOnClickListener { addNavigation(binding.categoria5, "Proteína") }
        binding.categoria6.setOnClickListener { addNavigation(binding.categoria6, "Aceite y grasas") }
        binding.categoria7.setOnClickListener { addNavigation(binding.categoria7, "Azúcar y dulces") }
        binding.categoria8.setOnClickListener { addNavigation(binding.categoria8, "Procesados") }
        binding.categoria9.setOnClickListener { addNavigation(binding.categoria9, "Bebidas") }

        binding.categoria10.setOnClickListener {
            productViewModel.getProducts()
            productViewModel.productsFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria10)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun addNavigation(view: View, category: String) {
        productViewModel.getProductsCategory(category)
        productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

            when (resource) {
                is Resource.Success -> {
                    products = resource.result.toObjects(Product::class.java)!!

                    val bundle = Bundle()
                    bundle.putSerializable("products", ArrayList(products))

                    Navigation.createNavigateOnClickListener(
                        R.id.action_mainPage_to_search2,
                        bundle
                    ).onClick(view)
                }

                is Resource.Failure -> {
                    Log.e("Error", "Fallo al descargar los datos de firestore")
                }

                else -> {}
            }
        }
    }

}
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainPage.newInstance] factory method to
 * create an instance of this fragment.
 */
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


        binding.categoria1.setOnClickListener {

            productViewModel.getProductsCategory("Cereales")
            productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        Log.e("cereales", "cereales")
                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria1)

                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }

        binding.categoria2.setOnClickListener {

            productViewModel.getProductsCategory("Verduras")
            productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria2)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }

        binding.categoria3.setOnClickListener {

            productViewModel.getProductsCategory("Lácteos")
            productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        Log.e("lacteos", products[0].name.toString())
                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria3)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }

        binding.categoria4.setOnClickListener {

            productViewModel.getProductsCategory("Fruta")
            productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria4)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }

        binding.categoria5.setOnClickListener {

            productViewModel.getProductsCategory("Proteína")
            productViewModel.productsFilterByCategoryFlow.observe(viewLifecycleOwner) { resource ->

                when (resource) {
                    is Resource.Success -> {
                        products = resource.result.toObjects(Product::class.java)!!

                        val bundle = Bundle()
                        bundle.putSerializable("products", ArrayList(products))

                        Navigation.createNavigateOnClickListener(
                            R.id.action_mainPage_to_search2,
                            bundle
                        ).onClick(binding.categoria5)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }


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
                        ).onClick(binding.categoria5)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }

    }



}
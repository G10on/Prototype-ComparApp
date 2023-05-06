package com.example.comparapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comparapp.adapter.CardViewAdapter
import com.example.comparapp.data.Product
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentSearchBinding
import com.example.comparapp.viewModel.ProductViewModel
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Search : Fragment() {

    private val viewModel: ProductViewModel by viewModels()
    private val authViewModel: UserViewModel by viewModels()
    private var productList: List<Product> = ArrayList()
    private lateinit var _binding: FragmentSearchBinding
    private lateinit var myAdapter: CardViewAdapter
    private lateinit var productRecyclerView: RecyclerView

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        productRecyclerView.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productList = arguments?.getSerializable("products") as List<Product>


        productRecyclerView = binding.recycleProducts
        productRecyclerView.layoutManager = LinearLayoutManager(activity)
        productRecyclerView.setHasFixedSize(false)


        if (productList != null) {

            authViewModel.getStatePremiumUser()
            authViewModel.userStatePremiumUser.observe(viewLifecycleOwner) {

                when (it) {
                    is Resource.Success -> {

                        myAdapter = CardViewAdapter(productList, it.result.toBoolean())
                        binding.recycleProducts.adapter = myAdapter

                    }

                    else -> {

                    }
                }
            }



        }
        /* Código para diferenciar entre una búsqueda empezada desde categoría o desde la barra
        else {

            viewModel.getProducts()
            viewModel.productsFlow.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        productList = resource.result.toObjects(Product::class.java)!!

                        authViewModel.getStatePremiumUser()
                        myAdapter = CardViewAdapter(productList, authViewModel.userStatePremiumUser.value.toString())
                        binding.recycleProducts.adapter = myAdapter
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al descargar los datos de firestore")
                    }

                    else -> {}
                }
            }

        }*/






    }





}
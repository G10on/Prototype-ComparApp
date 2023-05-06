package com.example.comparapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import java.util.Locale

@AndroidEntryPoint
class Search : Fragment() {

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
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
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
                        if (productList.isEmpty()){
                            binding.textoNoProducto.visibility = View.VISIBLE
                        }
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
    private fun filter(text: String) {
        val filteredlist = java.util.ArrayList<Product>()
        for (item in productList) {
            if (item.name?.lowercase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                filteredlist.add(item)
            }
        }
        myAdapter = CardViewAdapter(filteredlist, true)
        productRecyclerView.adapter = myAdapter
        if (filteredlist.isEmpty()) {
            binding.textoNoProducto.visibility = View.VISIBLE
        }
        else{
            binding.textoNoProducto.visibility = View.INVISIBLE
        }
    }





}
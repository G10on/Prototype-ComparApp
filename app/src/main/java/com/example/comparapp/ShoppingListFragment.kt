package com.example.comparapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentRegisterBinding
import com.example.comparapp.databinding.FragmentShoppingListBinding
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentShoppingListBinding
    private var isPremium: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        getState()


    }

    fun getState(): Boolean {
        viewModel.getStatePremiumUser()
        viewModel.userStatePremiumUser.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        isPremium = it.result.toBoolean()
                        if (isPremium) {
                            showShoppingList()
                            hideMessage()
                        } else {
                            hideShoppingList()
                            showMessage()
                        }
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al verificar su estado de Premium")
                    }

                    else -> {}
                }
            }
        }
        return isPremium
    }

    fun hideShoppingList(){
        binding.SLProductsList.visibility = View.INVISIBLE
    }
    fun showShoppingList(){
        binding.SLProductsList.visibility = View.VISIBLE
    }
    fun hideMessage(){
        binding.messagePremium.visibility = View.INVISIBLE
    }
    fun showMessage(){
        binding.messagePremium.visibility = View.VISIBLE
    }
}
package com.example.comparapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comparapp.data.Product
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentUserProfileBinding
import com.example.comparapp.viewModel.UserViewModel

class UserProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private var isPremium: Boolean = false
    private lateinit var _binding: FragmentUserProfileBinding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_profile, container, false)
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentUserProfileBinding.inflate(inflater)

        loadData()
        binding.btnChangePassword.setOnClickListener { changePassword() }
        binding.btnSubscribePremium.setOnClickListener { changeStatePremium() }
        binding.btnLogout.setOnClickListener { logout() }
    }

    private fun loadData() {
        var txtName = view?.findViewById<TextView>(R.id.txt_name)
        var txtEmail = view?.findViewById<TextView>(R.id.txt_email)

        txtName!!.text = userViewModel.getNameCurrentUser()
        txtEmail!!.text = userViewModel.getEmailCurrentUser()
    }

    private fun changePassword() {
        
    }

    private fun changeStatePremium() {

        userViewModel.getStatePremiumUser()

        userViewModel.userStatePremiumUser.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {

                    is Resource.Success -> {
                        var newState = it.result == "false"
                        userViewModel.setStatePremiumUser(newState)
                        updateSubscribeState()
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al intentar cambiar de contraseña")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun updateSubscribeState() {
        val btnSubscribe = requireView().findViewById<Button>(R.id.btn_subscribe_premium)
        userViewModel.getStatePremiumUser()
        userViewModel.userStatePremiumUser.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {

                    is Resource.Success -> {
                        var newTXT: String
                        newTXT = if (it.result.toBoolean()) {
                            "Desubscribe de Premium"
                        } else {
                            "Suscribirse a Premium"
                        }
                        btnSubscribe.setText(newTXT)
                    }

                    is Resource.Failure -> {
                        Log.e("Error", "Fallo al verificar su estado de Premium")
                    }

                    else -> {}
                }
            }
        }

    }

    private fun logout() {
        userViewModel.logout()
        findNavController().navigate(R.id.landingPage)
    }
}
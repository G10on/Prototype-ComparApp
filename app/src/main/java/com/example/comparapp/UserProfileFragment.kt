package com.example.comparapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comparapp.data.Product
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentSearchBinding
import com.example.comparapp.databinding.FragmentUserProfileBinding
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var _binding: FragmentUserProfileBinding

    private var isPremium: Boolean = false

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun loadData() {
        var txtName = view?.findViewById<TextView>(R.id.txt_name)
        var txtEmail = view?.findViewById<TextView>(R.id.txt_email)

        txtName!!.text = userViewModel.getNameCurrentUser()
        txtEmail!!.text = userViewModel.getEmailCurrentUser()
    }

    private fun changePassword() {
        userViewModel.changePassword()
        Toast.makeText(activity, "Email de cambio de contraseña enviado", Toast.LENGTH_SHORT).show()
    }

    private fun changeStatePremium() {
        var newState = !isPremium
        userViewModel.setStatePremiumUser(newState)
        if (newState) {
            Toast.makeText(activity, "Suscrito exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Desuscrito exitosamente", Toast.LENGTH_SHORT).show()
        }
        updateSubscribeState()
    }

    private fun updateSubscribeState() {
        val btnSubscribe = requireView().findViewById<Button>(R.id.btn_subscribe_premium)
        userViewModel.getStatePremiumUser()
        userViewModel.userStatePremiumUser.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {

                    is Resource.Success -> {
                        isPremium = it.result.toBoolean()
                        var newTXT: String = if (isPremium) {
                            "Desuscribirse de Premium"
                        } else {
                            "Suscribirse a Premium"
                        }
                        btnSubscribe.text = newTXT
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
        Toast.makeText(activity,"Cierre de sesión efectuado con éxito",Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.landingPage)
    }
}
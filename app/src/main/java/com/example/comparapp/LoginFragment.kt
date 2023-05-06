package com.example.comparapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentLoginBinding
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val userViewModel by viewModels<UserViewModel>()
    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // Setup a click listener for the Submit and go to Register Page buttons.
        binding.loginButton.setOnClickListener { iniciaSesion() }
        binding.registerRedirection.setOnClickListener { toRegister() }
    }

    fun toRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_register)
    }


    private fun iniciaSesion() {
        val emailInput = binding.emailLogin.text.toString()
        val passwordInput = binding.passwordLogin.text.toString()
        if (emailInput.isNotEmpty() && passwordInput.isEmpty()) {
            Toast.makeText(activity, "Contraseña requerida", Toast.LENGTH_SHORT).show()
        }
        else if (emailInput.isEmpty() && passwordInput.isNotEmpty()) {
            Toast.makeText(activity, "Correo requerido", Toast.LENGTH_SHORT).show()
        }
        else if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
            userViewModel.login(emailInput, passwordInput)
            userViewModel.loginFlow.observe(viewLifecycleOwner) {
                it?.let {
                    when (it) {
                        is Resource.Failure -> {
                            userViewModel.resetFlow()
                            Toast.makeText(activity,
                                "Inicio de sesión fallido",
                                Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            Toast.makeText(activity,
                                "Inicio de sesión efectuado con éxito",
                                Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.mainPage)

                        }
                    }
                }
            }
        } else {
            Toast.makeText(activity, "Los campos deben estar rellenos", Toast.LENGTH_SHORT).show()
        }
    }


}
package com.example.comparapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.comparapp.data.Resource
import com.example.comparapp.databinding.FragmentLoginBinding
import com.example.comparapp.utils.Utils
import com.example.comparapp.viewModel.UserViewModel

class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    private val utils = Utils()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        binding.userViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginButton.setOnClickListener { logIn() }
        return binding.root
    }

    private fun logIn() {
        val emailInput = binding.emailLogin.text.toString()
        val passwordInput = binding.passwordLogin.text.toString()

        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
                viewModel.login(emailInput, passwordInput)
                viewModel.loginFlow.observe(viewLifecycleOwner) {
                    it?.let {
                        when (it) {
                            is Resource.Failure -> {
                                viewModel.resetFlow()
                                utils.displayMessage(requireContext(), "Inicio de sesión fallido")
                            }
                            is Resource.Success -> {
                                utils.displayMessage(requireContext(), "Ha iniciado sesión")
                                //findNavController().navigate(R.id.homeSession)
                            }
                        }
                    }
                }
        } else {
            utils.displayMessage(requireContext(), "Todos los campos deben estar rellenados")
        }
    }

}
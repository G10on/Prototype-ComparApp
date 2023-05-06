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
import com.example.comparapp.databinding.FragmentRegisterBinding
import com.example.comparapp.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Register : Fragment() {
    private val viewModel: UserViewModel by viewModels()
    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // Setup a click listener for the Submit and go to Login Page buttons.
        binding.registerButton.setOnClickListener { signUp() }
        binding.registerRedirection.setOnClickListener { toLogin() }
    }

    fun toLogin() {
        findNavController().navigate(R.id.action_register_to_loginFragment)
    }

    private fun signUp() {
        val emailInput = binding.email.text.toString()
        val passwordInput = binding.password.text.toString()
        val repeatPasswordInput = binding.repeatPassword.text.toString()
        val nameInput = binding.name.text.toString()

        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty() && repeatPasswordInput.isNotEmpty() && nameInput.isNotEmpty()) {
            if (passwordInput == repeatPasswordInput) {
                viewModel.register(nameInput, emailInput, passwordInput)
                viewModel.signupFlow.observe(viewLifecycleOwner) {
                    it?.let {
                        when (it) {
                            is Resource.Failure -> {
                                viewModel.resetFlow()
                                if (it.exception.toString() == "ERROR_EMAIL_ALREADY_IN_USE") {

                                    Toast.makeText(activity,
                                        "El email ya se encuentra registrado",
                                        Toast.LENGTH_SHORT).show()
                                } else if (it.exception.toString() == "ERROR_INVALID_EMAIL") {
                                    Toast.makeText(activity,
                                        "El formato del email es inválido",
                                        Toast.LENGTH_SHORT).show()
                                } else if (it.exception.toString() == "ERROR_WEAK_PASSWORD") {
                                    Toast.makeText(activity,
                                        "La contraseña debe tener al menos 6 caracteres",
                                        Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(activity, it.exception, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            is Resource.Success -> {
                                Toast.makeText(activity,
                                    "Registro efectuado con éxito",
                                    Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.mainPage)

                            }
                        }
                    }
                }
            }
            else{
                Toast.makeText(activity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show()
        }


    }

}
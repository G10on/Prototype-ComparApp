package com.example.comparapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.comparapp.databinding.LandingPageBinding

class LandingPage : Fragment() {
    private lateinit var binding: LandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.landing_page, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.lifecycleOwner = viewLifecycleOwner
        // Setup a click listener for the Submit and go to Login Page buttons.
        binding.RegisterButtonLandingPage.setOnClickListener { toRegister() }
        binding.LoginButtonLandingPage.setOnClickListener { toLogin() }
    }

    fun toLogin() {
        //findNavController().navigate(R.id.action_homeNoSession_to_logIn)
    }

    fun toRegister() {
        //findNavController().navigate(R.id.action_homeNoSession_to_signUp)
    }
}





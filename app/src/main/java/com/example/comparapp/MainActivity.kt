package com.example.comparapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.comparapp.databinding.ActivityMainBinding
import com.example.comparapp.viewModel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNav()
        if (viewModel.currentUser != null) {
            //viewModel.logout() //Al no estar implementado el user profile si quieres probar a salir sesión descomenta la línea
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_landingPage_to_mainPage)
            showBottomNav()
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.nav.setupWithNavController(navController)
    }

    private fun setupNav() {
        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.nav)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainPage -> showBottomNav()
                R.id.shoppingListPage -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.nav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.nav.visibility = View.GONE

    }
}

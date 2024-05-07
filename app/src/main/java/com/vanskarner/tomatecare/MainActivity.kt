package com.vanskarner.tomatecare

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vanskarner.tomatecare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupView()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()
    }

    private fun setupView() {
        val bottomNavMain = binding.inclBottomNav
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        CustomNavigationBottomNav.setupView(bottomNavMain, navController)
    }

    private fun setupViewModel() {
        viewModel.bottomNavVisibility.observe(this) {
            val bindingBottomNav = binding.inclBottomNav
            val bindingBottomBackground = binding.viewBottomNavBackground
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_container) as NavHostFragment
            val navController = navHostFragment.navController
            when (it) {
                true -> CustomNavigationBottomNav.showBottomNav(
                    bindingBottomNav,
                    navController,
                    bindingBottomBackground
                )

                false -> CustomNavigationBottomNav.hideBottomNav(
                    bindingBottomNav,
                    bindingBottomBackground
                )
            }
        }
    }

}
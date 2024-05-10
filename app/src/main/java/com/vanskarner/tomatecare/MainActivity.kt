package com.vanskarner.tomatecare

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vanskarner.tomatecare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val customNavBottom = CustomNavigationBottomNav()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupView()
        setContentView(binding.root)
        setupViewModel()
    }

    private fun setupView() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        customNavBottom.navController = navController
        customNavBottom.viewBottomNavBackground = binding.viewBottomNavBackground
        customNavBottom.binding = binding.inclBottomNav
        customNavBottom.setupView()
    }

    private fun setupViewModel() {
        viewModel.showBottomNav.observe(this) { customNavBottom.show(it) }
        viewModel.hideBottomNav.observe(this) { customNavBottom.hide() }
    }

}
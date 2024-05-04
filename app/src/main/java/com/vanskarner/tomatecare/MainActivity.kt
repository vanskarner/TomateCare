package com.vanskarner.tomatecare

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vanskarner.tomatecare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainActivityListener {

    private lateinit var binding: ActivityMainBinding
    private var currentSelection = Selection.Start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        setupBottomNav(navController)
    }

    private fun setupBottomNav(nav: NavController) {
        binding.inclBottomNav.bottomNavStart.setOnClickListener {
            if (currentSelection != Selection.Start) {
                currentSelection = Selection.Start
                setBottomNavVisibility(true)
                nav.navigate(R.id.startNav)
            }
        }
        binding.inclBottomNav.bottomNavIdentify.setOnClickListener {
            if (currentSelection != Selection.Identification) {
                currentSelection = Selection.Identification
                setBottomNavVisibility(false)
                nav.navigate(R.id.identificationNav)
            }
        }
        binding.inclBottomNav.bottomNavDiseases.setOnClickListener {
            if (currentSelection != Selection.Diseases) {
                currentSelection = Selection.Diseases
                setBottomNavVisibility(true)
                nav.navigate(R.id.diseasesNav)
            }
        }
    }

    private fun setBottomNavVisibility(visible: Boolean) {
        when (visible) {
            true -> {
                binding.viewBottomNavBackground.visibility = View.VISIBLE
                binding.inclBottomNav.root.visibility = View.VISIBLE
            }

            false -> {
                binding.viewBottomNavBackground.visibility = View.GONE
                binding.inclBottomNav.root.visibility = View.GONE
            }
        }
    }

    override fun showBottomNav() {
        binding.viewBottomNavBackground.visibility = View.VISIBLE
        binding.inclBottomNav.root.visibility = View.VISIBLE
    }

}

enum class Selection {
    Start, Identification, Diseases
}
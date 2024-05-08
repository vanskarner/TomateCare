package com.vanskarner.tomatecare

import android.graphics.Typeface
import android.view.View
import androidx.navigation.NavController
import com.vanskarner.tomatecare.databinding.BottomnavMainBinding

class CustomNavigationBottomNav {

    private var currentSelection = Selection.Start
    private var previousSelection = Selection.Start
    lateinit var binding: BottomnavMainBinding
    lateinit var viewBottomNavBackground: View
    lateinit var navController: NavController

    fun setupView() {
        if (currentSelection != Selection.Start) {
            previousSelection = Selection.Diseases
            currentSelection = Selection.Diseases
            binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            navController.navigate(R.id.diseasesFragment)
        }
        binding.tvBottomNavStart.setOnClickListener { selectStart() }
        binding.bottomNavIdentify.setOnClickListener { selectIdentify() }
        binding.tvBottomNavDiseases.setOnClickListener { selectDiseases() }
    }

    fun hideBottomNav() {
        binding.root.visibility = View.GONE
        viewBottomNavBackground.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        when (previousSelection) {
            Selection.Start -> {
                previousSelection = Selection.Start
                currentSelection = Selection.Start
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
                navController.navigate(R.id.startFragment)
            }

            Selection.Diseases -> {
                previousSelection = Selection.Diseases
                currentSelection = Selection.Diseases
                binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
                navController.navigate(R.id.diseasesFragment)
            }

            Selection.Identification -> return
        }
    }

    fun showMarkerInDiseases() {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        previousSelection = Selection.Diseases
        currentSelection = Selection.Diseases
        binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
        binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
    }

    private fun selectStart() {
        if (currentSelection != Selection.Start) {
            previousSelection = Selection.Start
            currentSelection = Selection.Start
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
            navController.navigate(R.id.startFragment)
        }
    }

    private fun selectDiseases() {
        if (currentSelection != Selection.Diseases) {
            previousSelection = Selection.Diseases
            currentSelection = Selection.Diseases
            binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            navController.navigate(R.id.diseasesFragment)
        }
    }

    private fun selectIdentify() {
        currentSelection = Selection.Identification
        navController.navigate(R.id.captureFragment)
    }

}

enum class Selection {
    Start, Identification, Diseases
}
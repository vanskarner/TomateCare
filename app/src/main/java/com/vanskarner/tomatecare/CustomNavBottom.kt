package com.vanskarner.tomatecare

import android.graphics.Typeface
import android.view.View
import androidx.navigation.NavController
import com.vanskarner.tomatecare.databinding.BottomnavMainBinding

object CustomNavigationBottomNav {

    private var currentSelection = Selection.Start
    private var previousSelection = Selection.Start

    @JvmStatic
    fun setupView(
        binding: BottomnavMainBinding,
        nav: NavController
    ) {
        if (currentSelection != Selection.Start) {
            previousSelection = Selection.Diseases
            currentSelection = Selection.Diseases
            binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            nav.navigate(R.id.diseasesNav)
        }
        binding.tvBottomNavStart.setOnClickListener { selectStart(binding, nav) }
        binding.bottomNavIdentify.setOnClickListener { selectIdentify(nav) }
        binding.tvBottomNavDiseases.setOnClickListener { selectDiseases(binding, nav) }
    }

    fun hideBottomNav(
        binding: BottomnavMainBinding,
        viewBottomNavBackground: View
    ) {
        binding.root.visibility = View.GONE
        viewBottomNavBackground.visibility = View.GONE
    }

    fun showBottomNav(
        binding: BottomnavMainBinding,
        nav: NavController,
        viewBottomNavBackground: View
    ) {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        when (previousSelection) {
            Selection.Start -> {
                previousSelection = Selection.Start
                currentSelection = Selection.Start
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
                nav.navigate(R.id.startNav)
            }

            Selection.Diseases -> {
                previousSelection = Selection.Diseases
                currentSelection = Selection.Diseases
                binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
                nav.navigate(R.id.diseasesNav)
            }

            Selection.Identification -> return
        }
    }

    private fun selectStart(
        binding: BottomnavMainBinding,
        nav: NavController
    ) {
        if (currentSelection != Selection.Start) {
            previousSelection = Selection.Start
            currentSelection = Selection.Start
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
            nav.navigate(R.id.startNav)
        }
    }

    private fun selectDiseases(
        binding: BottomnavMainBinding,
        nav: NavController
    ) {
        if (currentSelection != Selection.Diseases) {
            previousSelection = Selection.Diseases
            currentSelection = Selection.Diseases
            binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            nav.navigate(R.id.diseasesNav)
        }
    }

    private fun selectIdentify(
        nav: NavController,
    ) {
        currentSelection = Selection.Identification
        nav.navigate(R.id.identificationNav)
    }

}

enum class Selection {
    Start, Identification, Diseases
}
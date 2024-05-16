package com.vanskarner.tomatecare.ui

import android.graphics.Typeface
import android.view.View
import androidx.navigation.NavController
import com.vanskarner.tomatecare.R
import com.vanskarner.tomatecare.databinding.BottomnavMainBinding

class CustomNavigationBottomNav {

    private var currentSelection = Selection.Start
    lateinit var binding: BottomnavMainBinding
    lateinit var viewBottomNavBackground: View
    lateinit var navController: NavController

    fun setupView() {
        binding.tvBottomNavStart.setOnClickListener { selectStart() }
        binding.bottomNavIdentify.setOnClickListener { selectIdentify() }
        binding.tvBottomNavDiseases.setOnClickListener { selectDiseases() }
    }

    fun hide() {
        binding.root.visibility = View.GONE
        viewBottomNavBackground.visibility = View.GONE
    }

    fun show(selection: Selection) {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        when (selection) {
            Selection.Start -> {
                currentSelection = Selection.Start
                binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
            }

            Selection.Identification -> {
                if (currentSelection == Selection.Start) navController.navigate(R.id.startFragment)
                else navController.navigate(R.id.diseasesFragment)
            }

            Selection.Diseases -> {
                currentSelection = Selection.Diseases
                binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            }
        }
    }

    private fun setSelection(selection: Selection) {
        when (selection) {
            Selection.Start -> {
                currentSelection = Selection.Start
                binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
            }

            Selection.Identification -> return
            Selection.Diseases -> {
                currentSelection = Selection.Diseases
                binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
                binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            }
        }
    }

    private fun selectStart() {
        if (currentSelection != Selection.Start) {
            setSelection(Selection.Start)
            navController.navigate(R.id.startFragment)
        }
    }

    private fun selectIdentify() {
        setSelection(Selection.Identification)
        navController.navigate(R.id.captureFragment)
    }

    private fun selectDiseases() {
        if (currentSelection != Selection.Diseases) {
            setSelection(Selection.Diseases)
            navController.navigate(R.id.diseasesFragment)
        }
    }

}

enum class Selection {
    Start, Identification, Diseases
}
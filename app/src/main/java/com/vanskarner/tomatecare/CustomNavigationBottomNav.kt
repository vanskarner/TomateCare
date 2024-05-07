package com.vanskarner.tomatecare

import android.graphics.Typeface
import android.view.View
import androidx.navigation.NavController
import com.vanskarner.tomatecare.databinding.BottomnavMainBinding

object CustomNavigationBottomNav {

    private var currentSelection = Selection.Start
    private var previusSelection = Selection.Start

    @JvmStatic
    fun setupView(
        binding: BottomnavMainBinding,
        nav: NavController,
        bottomBackground: View
    ) {
        binding.tvBottomNavStart.setOnClickListener { selectStart(binding, nav, bottomBackground) }
        binding.bottomNavIdentify.setOnClickListener {
            selectIdentify(
                binding,
                nav,
                bottomBackground
            )
        }
        binding.tvBottomNavDiseases.setOnClickListener {
            selectDiseases(
                binding,
                nav,
                bottomBackground
            )
        }
    }

    @JvmStatic
    fun onBackPressed(
        binding: BottomnavMainBinding,
        nav: NavController,
        viewBottomNavBackground: View
    ) {
        if (currentSelection == Selection.Identification) {
            when (previusSelection) {
                Selection.Start -> selectStart(binding, nav, viewBottomNavBackground)

                Selection.Diseases -> selectDiseases(binding, nav, viewBottomNavBackground)

                Selection.Identification -> return
            }
        }
    }

    private fun selectStart(
        binding: BottomnavMainBinding,
        nav: NavController,
        viewBottomNavBackground: View
    ) {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        if (currentSelection != Selection.Start) {
            previusSelection = Selection.Start
            currentSelection = Selection.Start
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavStart.setTypeface(null, Typeface.BOLD)
            nav.navigate(R.id.startNav)
        }
    }

    private fun selectDiseases(
        binding: BottomnavMainBinding,
        nav: NavController,
        viewBottomNavBackground: View
    ) {
        binding.root.visibility = View.VISIBLE
        viewBottomNavBackground.visibility = View.VISIBLE
        if (currentSelection != Selection.Diseases) {
            previusSelection = Selection.Diseases
            currentSelection = Selection.Diseases
            binding.tvBottomNavStart.setTypeface(null, Typeface.NORMAL)
            binding.tvBottomNavDiseases.setTypeface(null, Typeface.BOLD)
            nav.navigate(R.id.diseasesNav)
        }
    }

    private fun selectIdentify(
        binding: BottomnavMainBinding,
        nav: NavController,
        viewBottomNavBackground: View
    ) {
        currentSelection = Selection.Identification
        binding.root.visibility = View.GONE
        viewBottomNavBackground.visibility = View.GONE
        nav.navigate(R.id.identificationNav)
    }

}

enum class Selection {
    Start, Identification, Diseases
}
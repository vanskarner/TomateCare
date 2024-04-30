package com.vanskarner.tomatecare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanskarner.tomatecare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val infoDialog = InfoDialog()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInfo.setOnClickListener { infoDialog.show(supportFragmentManager) }
    }

}
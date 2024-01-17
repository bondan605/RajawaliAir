package com.rajawali.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rajawali.app.databinding.FragmentDetailsInformationBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentDetailsInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.rajawali.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rajawali.app.databinding.FragmentBookingPageBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentBookingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
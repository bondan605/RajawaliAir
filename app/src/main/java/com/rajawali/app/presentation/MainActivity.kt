package com.rajawali.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rajawali.app.databinding.FragmentSelectedTicketBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentSelectedTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSelectedTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
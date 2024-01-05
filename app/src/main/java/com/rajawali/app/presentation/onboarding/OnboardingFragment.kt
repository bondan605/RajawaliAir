package com.rajawali.app.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rajawali.app.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private val binding: FragmentOnboardingBinding get() = _binding!!
    private var _binding: FragmentOnboardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetStarted.setOnClickListener {
            Toast.makeText(activity, "pindah", Toast.LENGTH_SHORT).show()
        }

//        binding.includeToolbar.tvSkip.setOnClickListener {
//            binding.motionLayout.transitionToEnd()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
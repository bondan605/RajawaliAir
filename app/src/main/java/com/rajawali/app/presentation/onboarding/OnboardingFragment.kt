package com.rajawali.app.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private val binding: FragmentOnboardingBinding get() = _binding!!
    private var _binding: FragmentOnboardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO 1: Login layout is needed.
        binding.btnGetStarted.setOnClickListener {
            view.findNavController().navigate(R.id.action_onboardingFragment_to_homePageFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.rajawali.app.presentation.splash

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        //setting status bar
        activity?.window?.statusBarColor = Color.WHITE
        val windowInsetController = WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
        windowInsetController.isAppearanceLightStatusBars = true

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        automaticallyNavigate()
    }

    private fun automaticallyNavigate() {
        CoroutineScope(Dispatchers.Main).launch {
            // 2 sec delay
            delay(2000)
            view?.findNavController()?.navigate(R.id.action_splashFragment_to_onboardingFragment)
        }
    }
}
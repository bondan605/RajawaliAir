package com.rajawali.app.presentation.splash

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentSplashBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.result.CommonResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentSplashBinding? = null

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        //setting status bar
        activity?.window?.statusBarColor = Color.WHITE
        val windowInsetController = WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        )
        windowInsetController.isAppearanceLightStatusBars = true

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        isLogin()
    }

    private fun isLogin() {
        splashViewModel.isLogin.observe(viewLifecycleOwner) { isLogin ->
            val direction = navigateDirection(isLogin)

            automaticallyNavigate(direction)
        }
    }

    private fun automaticallyNavigate(directions: NavDirections) {
        viewLifecycleOwner.lifecycleScope.launch {
            // 2 sec delay
            delay(2000)
            findNavController().safeNavigate(directions)
        }
    }

    private fun navigateDirection(isLogin: CommonResult<String>) : NavDirections =
        when (isLogin) {
            is CommonResult.Error ->
                SplashFragmentDirections.actionSplashFragmentToOnboardingFragment()
            is CommonResult.Success ->
                SplashFragmentDirections.actionSplashFragmentToHomePageFragment()
        }
}
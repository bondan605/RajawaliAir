package com.rajawali.app.presentation.ui.auth.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentVerificationBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.result.CommonResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerificationFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentVerificationBinding? = null

    private val verifyViewModel: VerifyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        setOnBtnVerifyClicked()

        setOnBtnBackClicked()
    }

    private fun setOnBtnBackClicked() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnBtnVerifyClicked() {
        binding.btnVerify.setOnClickListener {
            val firstText = binding.etOtp1.text.toString()
            val secondText = binding.etOtp2.text.toString()
            val thirdText = binding.etOtp3.text.toString()
            val fourthText = binding.etOtp4.text.toString()
            val fiveText = binding.etOtp5.text.toString()
            val sixText = binding.etOtp6.text.toString()
            val otp = firstText + secondText + thirdText + fourthText + fiveText + sixText
            val email = getEmail()

            verifyViewModel.verify(otp, email).observe(viewLifecycleOwner) { status ->

                when (status) {
                    is CommonResult.Error ->
                        Toast.makeText(activity, getString(R.string.tv_top_code_wrong), Toast.LENGTH_SHORT).show()

                    is CommonResult.Success -> {
                        Toast.makeText(activity, status.data, Toast.LENGTH_SHORT).show()

                        viewLifecycleOwner.lifecycleScope.launch {
                            delay(1000)
                            navigateToLogin()
                        }
                    }
                }
            }

        }
    }

    private fun navigateToLogin() {
        val destination = VerificationFragmentDirections.actionVerificationFragmentToLoginFragment()

        findNavController().safeNavigate(destination)
    }

    private fun getEmail(): String =
        VerificationFragmentArgs.fromBundle(arguments as Bundle).email


}

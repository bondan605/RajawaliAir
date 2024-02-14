package com.rajawali.app.presentation.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentRegisterBinding
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.result.CommonResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentRegisterBinding? = null

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here


        setOnBtnRegisterClicked()
        setOnBtnBackClicked()
    }

    private fun setOnBtnBackClicked() {
        binding.ivBack.setOnClickListener {
            navigate()
        }
    }

    private fun setOnBtnRegisterClicked() {
        binding.btnRegister.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val fullName = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            registerViewModel.register(
                fullName = fullName,
                email = email,
                phone = phone,
                password = password,
                confirmPassword = confirmPassword,
            ).observe(viewLifecycleOwner) { status ->

                when (status) {
                    is CommonResult.Error -> {
                        Toast.makeText(activity, "Register failed", Toast.LENGTH_SHORT).show()
                    }

                    is CommonResult.Success -> {
                        Toast.makeText(activity, status.data, Toast.LENGTH_SHORT).show()

                        viewLifecycleOwner.lifecycleScope.launch {
                            delay(1000)
                            navigateToVerification(email)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToVerification(email: String) {
        val destination =
            RegisterFragmentDirections
                .actionRegisterFragmentToVerificationFragment(email)

        findNavController().safeNavigate(destination)
    }

    private fun navigate() {
        AppUtils.navigate(view, R.id.action_registerFragment_to_loginFragment)
    }

}
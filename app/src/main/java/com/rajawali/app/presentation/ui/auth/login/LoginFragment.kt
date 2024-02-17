package com.rajawali.app.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentLoginBinding
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLoginBinding? = null

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        onBtnSkipClicked()

        onBtnLoginClicked()

        onBtnRegisterClicked()
    }


    private fun onBtnLoginClicked() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            loginViewModel.login(email, password).observe(viewLifecycleOwner) { response ->

                when (response) {
                    is CommonResult.Error ->
                        setToast(response.errorMessage)

                    is CommonResult.Success -> {
                        val accessToken = response.data.accessToken
                        val id = response.data.id

                        loginViewModel.createLoginSession(accessToken, id)
                            .observe(viewLifecycleOwner) { isLoginSession ->

                                when (isLoginSession) {
                                    true ->
                                        navigate()

                                    false ->
                                        setToast(Constant.LOGIN_FAILED)
                                }
                            }
                    }
                }
            }

        }
    }

    private fun navigate() {
        val destination =
            LoginFragmentDirections
                .actionLoginFragmentToHomePageFragment()

        findNavController().safeNavigate(destination)

    }

    private fun setToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun onBtnRegisterClicked() {
        binding.llRegister.setOnClickListener {
            AppUtils.navigate(view, R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun onBtnSkipClicked() {
        binding.tvSkip.setOnClickListener {
            AppUtils.navigate(view, R.id.action_loginFragment_to_homePageFragment)
        }
    }


}
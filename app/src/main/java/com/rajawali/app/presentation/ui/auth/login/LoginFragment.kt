package com.rajawali.app.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentLoginBinding
import com.rajawali.app.util.AppUtils

class LoginFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.tvSkip.setOnClickListener {
            AppUtils.navigate(view, R.id.action_loginFragment_to_homePageFragment)
        }

        binding.btnLogin.setOnClickListener {
            AppUtils.navigate(view, R.id.action_loginFragment_to_homePageFragment)
        }

        binding.llRegister.setOnClickListener {
            AppUtils.navigate(view, R.id.action_loginFragment_to_registerFragment)
        }
    }

}
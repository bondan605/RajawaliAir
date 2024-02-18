package com.rajawali.app.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajawali.app.databinding.FragmentNotificationBinding
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.NotAvailableEnum
import com.rajawali.core.domain.model.IsLoginModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.presentation.adapter.NotificationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val notificationViewModel: NotificationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        isLogin()

        setOnBackClicked()
    }

    private fun setOnBackClicked() {
        binding.includeToolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun isLogin() {
        notificationViewModel.getLoggedInData.observe(viewLifecycleOwner) { login ->

            when (login) {
                is CommonResult.Error ->
                    navigate()

                is CommonResult.Success ->
                    setNotificationRecyclerview(login.data)
            }
        }

    }

    private fun navigate() {
        val destination =
            NotificationFragmentDirections.actionNotificationFragmentToNotLoginSheetDialog(NotAvailableEnum.LOGIN)

        findNavController().safeNavigate(destination)
    }

    private fun setNotificationRecyclerview(data: IsLoginModel) {
        val rvPromotion = binding.rvNotification
        val _adapter = NotificationAdapter()
        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        notificationViewModel.getNotification(id = data.id, accessToken = data.accessToken)
            .observe(viewLifecycleOwner) { notif ->

                when (notif) {
                    is CommonResult.Error -> {}
                    is CommonResult.Success ->
                        _adapter.submitList(notif.data.notifications)
                }

            }

        rvPromotion.apply {
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }

    }

}
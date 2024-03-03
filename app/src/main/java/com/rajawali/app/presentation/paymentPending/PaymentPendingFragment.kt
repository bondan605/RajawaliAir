package com.rajawali.app.presentation.paymentPending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentPaymentPendingBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.util.DateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentPendingFragment : Fragment() {
    private var _binding: FragmentPaymentPendingBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val paymentPending: PaymentPendingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentPendingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        setOnBackClicked()

        populatePaymentInformationCard()

        setPaymentPending()
    }

    private fun setPaymentPending() {
        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            paymentPending.isApprove(payment.reservation.id)

            paymentPending.paymentApprove.observe(viewLifecycleOwner) { paymentStatus ->

                if (paymentStatus.isSuccess)
                    navigate()
            }
        }
    }

    private fun navigate() {
        val destination = PaymentPendingFragmentDirections.actionPaymentPendingFragmentToPaymentCompleteFragment()

        findNavController().safeNavigate(destination)
    }

    private fun populatePaymentInformationCard() {
        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            val purchaseDate = DateFormat.formatDateStringToAbbreviatedString(payment.createdAt)
            val orderId = getString(com.rajawali.common_resource.R.string.tv_order_id_value, payment.reservation.id)
            val method = payment.method.replace("_", " ", ignoreCase = true).lowercase().capitalize()

            binding.tvPurchaseDateValue.text = purchaseDate
            binding.tvPaymentMethodValue.text = method
            binding.tvTotalPriceValue.text =
                getString(com.rajawali.common_resource.R.string.tv_total_price, payment.reservation.totalPrice)

            setOrderId(orderId)
        }
    }

    private fun setOrderId(text: String) {
        binding.includeToolbar.tvAppBarLabel.text = text
        binding.tvOrderIdValue.text = text
    }

    private fun setOnBackClicked() {
        binding.includeToolbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
package com.rajawali.app.presentation.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentPaymentBinding
import com.rajawali.app.presentation.bottomSheetDialog.paymentMethod.PaymentMethodViewModel
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.app.util.Payment
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.DateFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

//    private val ticketViewModel: TicketViewModel by activityViewModels()
//    private val paymentMethodViewModel: PaymentMethodViewModel by activityViewModels()
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val paymentMethodViewModel: PaymentMethodViewModel by navGraphViewModels(R.id.nav_booking)
    private val paymentViewModel: PaymentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        populateTicketCard()

        populatePaymentMethod()

        setPaymentReservationId()
        setPaymentMethod()

        isBtnPayAvailable()
        setOnBtnPayClicked()

        onBtnMorePaymentMethodClicked()

        setOnBtnForwardClicked()

        startPaymentTimer()
        populatePaymentTimerView()

        populateAppBarView()
    }

    private fun setOnBtnForwardClicked() {
        binding.itemTicket.setOnClickListener {
            ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
                val destination = PaymentFragmentDirections
                    .actionPaymentFragmentToFlightDetailBottomSheetDialog(ticket)

                findNavController().safeNavigate(destination)
            }
        }
    }

    private fun populateAppBarView() {
        val included = binding.includeAppbar

        ticketViewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            included.tvOrderId.text = getString(R.string.tv_order_id_value, reservation.id)
        }

        included.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPaymentReservationId() {
        ticketViewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            paymentViewModel.updateReservationId(reservation.id)
        }
    }

    private fun setPaymentMethod() {
        paymentMethodViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            paymentViewModel.updatePaymentMethod(method.name)
        }
    }

    private fun isBtnPayAvailable() {
        CoroutineScope(Dispatchers.Main).launch {
            paymentViewModel.paymentViewState.collect { state ->
                binding.btnPay.isEnabled =
                    state.reservationId.isNotEmpty() && state.paymentMethod.isNotEmpty()
            }
        }
    }

    private fun setOnBtnPayClicked() {

        binding.btnPay.setOnClickListener {

            paymentViewModel.payTicket(
                paymentViewModel.paymentViewState.value.reservationId,
                paymentViewModel.paymentViewState.value.paymentMethod
            ).observe(viewLifecycleOwner) { payment ->

                when (payment) {
                    is CommonResult.Error ->
                        Toast.makeText(
                            activity,
                            "Unable to pay. Check your Network",
                            Toast.LENGTH_SHORT
                        ).show()

                    is CommonResult.Success -> {
                        val destination = PaymentFragmentDirections
                            .actionPaymentFragmentToPaymentWaitingFragment()

                        ticketViewModel.setPayment(payment.data)
                        findNavController().safeNavigate(destination)
                    }
                }

            }
        }
    }

    private fun onBtnMorePaymentMethodClicked() {
        binding.btnMoreMethod.setOnClickListener {
            val destination =
                PaymentFragmentDirections.actionPaymentFragmentToPaymentMethodBottomSheetDialog()

            findNavController().safeNavigate(destination)
        }

    }


    private fun startPaymentTimer() {
        ticketViewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            val expired = reservation.expiredAt
            ticketViewModel.startTimer(expired)
        }
    }

    private fun populatePaymentTimerView() {
        ticketViewModel.paymentTimer.observe(viewLifecycleOwner) { timer ->
            val hours = TimeUnit.MILLISECONDS.toHours(timer)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timer % TimeUnit.HOURS.toMillis(1))
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timer % TimeUnit.MINUTES.toMillis(1))

            binding.jam.text = hours.toString()
            binding.menit.text = minutes.toString()
            binding.detik.text = seconds.toString()
        }
    }

    private fun populateTicketCard() {
        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
            val date = DateFormat.formatDateStringToAbbreviatedString(ticket.departureDate)

            binding.tvDepartureCityCodeLabel.text = ticket.sourceAirport.cityCode
            binding.tvArriveCityCodeLabel.text = ticket.destinationAirport.cityCode

            binding.directFlight.text = getString(
                R.string.tv_date_departure_time_arrival_time_value,
                date,
                ticket.departureTime,
                ticket.arrivalTime
            )

            binding.ticketType.text =
                getString(R.string.flight_seat_type_value, ticket.classType.capitalize())
        }
    }

    private fun populatePaymentMethod() {
        paymentMethodViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            val preferredPaymentMethod = Payment.populatePaymentList.find { it.method == method }

            if (preferredPaymentMethod != null) {
                binding.tvPaymentMethodLabel.text = getString(preferredPaymentMethod.name)
                binding.ivPaymentMethodLogo.setImageResource(preferredPaymentMethod.logo)
            }
        }
    }

//    private fun populatePaymentMethod() {
//        paymentMethodViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
//            val logo = when (method) {
//                BCA_TRANSFER ->
//                    com.rajawali.core.R.drawable.png_bca_logo
//
//                BRI_TRANSFER ->
//                    com.rajawali.core.R.drawable.png_bri_logo
//
//                BNI_TRANSFER ->
//                    com.rajawali.core.R.drawable.png_bni_logo
//
//                MANDIRI_TRANSFER ->
//                    com.rajawali.core.R.drawable.png_mandiri_logo
//
//                BCA_VIRTUAL ->
//                    com.rajawali.core.R.drawable.png_bca_logo
//
//                BRI_VIRTUAL ->
//                    com.rajawali.core.R.drawable.png_bri_logo
//
//                BNI_VIRTUAL ->
//                    com.rajawali.core.R.drawable.png_bni_logo
//
//                MANDIRI_VIRTUAL ->
//                    com.rajawali.core.R.drawable.png_mandiri_logo
//
//                ALFAMART ->
//                    com.rajawali.core.R.drawable.png_alfamart_logo
//
//                INDOMARERT ->
//                    com.rajawali.core.R.drawable.png_indomaret_logo
//
//                null ->
//                    R.drawable.ic_ellipse_filled
//            }
//
//            val text = when (method) {
//                BCA_TRANSFER ->
//                    com.rajawali.core.R.string.tv_bca_transfer_label
//
//                BRI_TRANSFER ->
//                    com.rajawali.core.R.string.tv_bri_transfer_label
//
//                BNI_TRANSFER ->
//                    com.rajawali.core.R.string.tv_bni_transfer_label
//
//                MANDIRI_TRANSFER ->
//                    com.rajawali.core.R.string.tv_mandiri_transfer_label
//
//                BCA_VIRTUAL ->
//                    com.rajawali.core.R.string.tv_bca_virtual_label
//
//                BRI_VIRTUAL ->
//                    com.rajawali.core.R.string.tv_bri_virtual_label
//
//                BNI_VIRTUAL ->
//                    com.rajawali.core.R.string.tv_bni_virtual_label
//
//                MANDIRI_VIRTUAL ->
//                    com.rajawali.core.R.string.tv_mandiri_virtual_label
//
//                ALFAMART ->
//                    com.rajawali.core.R.string.tv_alfamart_label
//
//                INDOMARERT ->
//                    com.rajawali.core.R.string.tv_indomaret_label
//
//                null ->
//                    com.rajawali.core.R.string.tv_indomaret_label
//
//
//
//            }
//        }
//    }
}

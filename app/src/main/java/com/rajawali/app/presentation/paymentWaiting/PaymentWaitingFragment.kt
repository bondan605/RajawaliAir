package com.rajawali.app.presentation.paymentWaiting

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentPaymentWaitingBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.presentation.payment.PaymentFragmentDirections
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.app.util.Payment
import com.rajawali.core.domain.model.PopulatePaymentMethodModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.util.Constant
import com.rajawali.core.util.DateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PaymentWaitingFragment : Fragment() {
    private var _binding: FragmentPaymentWaitingBinding? = null
    private val binding get() = _binding!!

    //    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)

    //    private val paymentMethodViewModel: PaymentMethodViewModel by activityViewModels()
    private val paymentViewModel: PaymentWaitingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentWaitingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here
        setPayIcon()

        populateTicketCard()

        populatePaymentInformation()

        setOnTvPaymentNumberClicked()

        setOnTransferViaAtmClicked()
        setOnInternetViaAtmClicked()
        setOnMobileViaAtmClicked()

        onTransferViaAtmDropdownIcon()
        onTransferViaInternetDropdownIcon()
        onTransferViaMobileDropdownIcon()
        setContentVisibility()

        setTimerView()

        populateAppBarView()

        setOnBtnAlreadyPaidClicked()

        setOnBtnForwardClicked()
    }

    private fun setOnBtnForwardClicked() {
        binding.includeTicketPreview.root.setOnClickListener {
            ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
                val destination = PaymentFragmentDirections
                    .actionPaymentFragmentToFlightDetailBottomSheetDialog(ticket)

                findNavController().safeNavigate(destination)
            }
        }
    }


    private fun findPaymentMethodName(method: String): PopulatePaymentMethodModel? {
        val paymentMethod = method.replace(" ", "_", ignoreCase = true).lowercase()
        return Payment.populatePaymentList.find { it.method.name.lowercase() == paymentMethod }

    }

    private fun setOnBtnAlreadyPaidClicked() {
        binding.btnAlreadyPaid.setOnClickListener {

            //loading visible
            setLoadingVisibility(true)

            ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
                paymentViewModel.finishPayment(payment.id)
                    .observe(viewLifecycleOwner) { paymentStatus ->
                        Timber.d("setOnBtnAlreadyPaidClicked: $paymentStatus")

                        when (paymentStatus) {
                            is CommonResult.Error -> {
                                //loading visibility
                                setLoadingVisibility(false)
                                setToast(Constant.PAYMENT_NO_NETWORK)
                            }

                            is CommonResult.Success -> {
                                //loading visibility
                                setLoadingVisibility(false)
                                navigate()
                            }
                        }

                    }
            }
        }

    }

    private fun navigate() {
        val destination = PaymentWaitingFragmentDirections.actionPaymentWaitingFragmentToPaymentPendingFragment()

        findNavController().safeNavigate(destination)
    }


//    private fun setOnBtnAlreadyPaidClicked() {
//        binding.btnAlreadyPaid.setOnClickListener {
//
//            //loading visible
//            setLoadingVisibility(true)
//
//            ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
//                paymentViewModel.getReservationById(payment.reservation.id)
//                .observe(viewLifecycleOwner) { paymentStatus ->
//                    Timber.d("setOnBtnAlreadyPaidClicked: $paymentStatus")
//
//                    when (paymentStatus) {
//                        is CommonResult.Error -> {
//                            //loading visibility
//                            setLoadingVisibility(false)
//                            setToast(Constant.PAYMENT_NO_NETWORK)
//                        }
//
//                        is CommonResult.Success -> {
//                            val status = paymentStatus.data.paymentStatus
//                            //loading visibility
//                            setLoadingVisibility(false)
//
//                            when (paymentViewModel.getPaymentStatus(status)) {
//                                PaymentStatusEnum.PAYMENT_WAITING ->
//                                    setToast(status)
//
//                                PaymentStatusEnum.PAYMENT_PENDING ->
//                                    setToast(status)
//
//                                PaymentStatusEnum.PAYMENT_SUCCESS -> {
//
//                                    val destination =
//                                        PaymentWaitingFragmentDirections.actionPaymentWaitingFragmentToPaymentCompleteFragment()
//                                    findNavController().safeNavigate(destination)
//                                }
//
//                                else -> {
//                                    val destination =
//                                        PaymentWaitingFragmentDirections
//                                            .actionPaymentWaitingFragmentToNotAvailableBottomSheetDialog(NotAvailableEnum.PAYMENT)
//
//                                    findNavController().safeNavigate(destination)
//
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
//
//    }

    private fun setToast(text: String) {
        Toast.makeText(
            activity,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setLoadingVisibility(isVisible: Boolean) {
        when (isVisible) {
            true ->
                binding.ivLoadingScreen.visibility = View.VISIBLE

            false ->
                binding.ivLoadingScreen.visibility = View.GONE

        }
    }

    private fun populateAppBarView() {
        val included = binding.includeAppbar

        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            val preferredPaymentMethod = findPaymentMethodName(payment.method)

            if (preferredPaymentMethod != null)
                included.tvAppBarLabel.text = getString(preferredPaymentMethod.name)

            included.tvOrderId.text = getString(com.rajawali.common_resource.R.string.tv_order_id_value, payment.reservation.id)
        }

        included.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setTimerView() {
        ticketViewModel.paymentTimer.observe(viewLifecycleOwner) { timer ->
            val included = binding.includeTicketPreview
            val hours = TimeUnit.MILLISECONDS.toHours(timer)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timer % TimeUnit.HOURS.toMillis(1))
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timer % TimeUnit.MINUTES.toMillis(1))

            included.jam.text = hours.toString()
            included.menit.text = minutes.toString()
            included.detik.text = seconds.toString()
        }
    }

    private fun setContentVisibility() {
        paymentViewModel.transferDropdown.observe(viewLifecycleOwner) { state ->
            binding.includeStepsViaAtm.root.visibility = state.setDropDownContentVisibility()
        }

        paymentViewModel.internetDropdown.observe(viewLifecycleOwner) { state ->
            binding.includeStepsViaInternet.root.visibility = state.setDropDownContentVisibility()
        }

        paymentViewModel.mobileDropdown.observe(viewLifecycleOwner) { state ->
            binding.includeStepsViaMobile.root.visibility = state.setDropDownContentVisibility()
        }
    }

    private fun setOnTransferViaAtmClicked() {
        binding.clTransferViaAtm.setOnClickListener {
            paymentViewModel.setTransferDropdown()
        }
    }

    private fun setOnInternetViaAtmClicked() {
        binding.clTransferViaInternet.setOnClickListener {
            paymentViewModel.setInternetDropdown()
        }
    }

    private fun setOnMobileViaAtmClicked() {
        binding.clTransferViaMobile.setOnClickListener {
            paymentViewModel.setMobileDropdown()
        }
    }

    private fun onTransferViaAtmDropdownIcon() {
        paymentViewModel.transferDropdown.observe(viewLifecycleOwner) { state ->
            val icon = state.setDropDownIcon()

            binding.ivViaAtmArrow.setImageResource(icon)
        }
    }

    private fun onTransferViaInternetDropdownIcon() {
        paymentViewModel.internetDropdown.observe(viewLifecycleOwner) { state ->
            val icon = state.setDropDownIcon()

            binding.ivViaInternetArrow.setImageResource(icon)
        }
    }

    private fun onTransferViaMobileDropdownIcon() {
        paymentViewModel.mobileDropdown.observe(viewLifecycleOwner) { state ->
            val icon = state.setDropDownIcon()

            binding.ivViaMobileArrow.setImageResource(icon)
        }
    }

    private fun Boolean.setDropDownIcon(): Int =
        when (this) {
            true ->
                com.rajawali.common_resource.R.drawable.ic_arrow_up

            false ->
                com.rajawali.common_resource.R.drawable.ic_arrow_down
        }

    private fun Boolean.setDropDownContentVisibility(): Int =
        when (this) {
            true ->
                View.VISIBLE

            false ->
                View.GONE
        }


    private fun populatePaymentTimer() {
        ticketViewModel.paymentTimer.observe(viewLifecycleOwner) { timer ->
            val included = binding.includeTicketPreview
            val localTime = ticketViewModel.timerLongToLocalTime(timer)
            val hour = localTime.hour
            val minute = localTime.minute
            val second = localTime.second

            included.jam.text = hour.toString()
            included.menit.text = minute.toString()
            included.detik.text = second.toString()
        }

    }

    private fun setOnTvPaymentNumberClicked() {
        binding.tvPaymentNumber.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = binding.tvPaymentNumber.text.toString()
            val clipData = ClipData.newPlainText(COPIED_PAYMENT_NUMBER, text)

            clipboard.setPrimaryClip(clipData)
        }
    }

    private fun setPayIcon() {
        val include = binding.includePaymentSteps

        include.ivStepTwo.setImageResource(com.rajawali.common_resource.R.drawable.ic_number_two_circle_filled)
    }

    private fun populateTicketCard() {
        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { ticket ->
            val included = binding.includeTicketPreview
            val date = DateFormat.formatDateStringToAbbreviatedString(ticket.departureDate)

            included.tvDepartureCityCodeLabel.text = ticket.sourceAirport.cityCode
            included.tvArriveCityCodeLabel.text = ticket.destinationAirport.cityCode

            included.directFlight.text = getString(
                com.rajawali.common_resource.R.string.tv_date_departure_time_arrival_time_value,
                date,
                ticket.departureTime,
                ticket.arrivalTime
            )

            included.ticketType.text =
                getString(
                    com.rajawali.common_resource.R.string.flight_seat_type_value, ticket.classType.capitalize())
        }
    }

    private fun populatePaymentInformation() {
        ticketViewModel.payment.observe(viewLifecycleOwner) { payment ->
            val preferredPaymentMethod = findPaymentMethodName(payment.method)

            if (preferredPaymentMethod != null) {
                binding.tvPaymentMethodLabel.text = getString(preferredPaymentMethod.name)
                binding.ivPaymentMethodLogo.setImageResource(preferredPaymentMethod.logo)
                binding.tvPaymentNumber.text = payment.receiverNumber
                binding.tvTotalPaymentAmount.text =
                    getString(com.rajawali.common_resource.R.string.tv_total_price, payment.reservation.totalPrice)
            }


        }
    }

    companion object {
        const val COPIED_PAYMENT_NUMBER = "Rajawali Ticket Payment Number"
    }
}
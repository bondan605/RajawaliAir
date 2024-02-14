package com.rajawali.app.presentation.detailsInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentDetailsInformationBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.util.AppUtils
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.GenderEnum
import com.rajawali.core.domain.model.PassengerInputModel
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.presentation.adapter.PassengerInputAdapter
import com.rajawali.core.util.DateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailsInformationFragment : Fragment() {
    private var _binding: FragmentDetailsInformationBinding? = null
    private val binding get() = _binding!!

    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val detailsInformationViewModel: DetailsInformationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here

        //TODO reset the passenger details live data
        populateView()

        createPassengerDetailInput()

        getCheckboxGenderAndId()

        onBtnNextPageClicked()
    }

    private fun onBtnNextPageClicked() {
        binding.btnNextPage.setOnClickListener {
            var passengerAmount = 0
            var passengerList = 0
            val fullName = binding.etFullName.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            var gender = ""

            ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) {
                passengerAmount = it.adultPassenger + it.childPassenger + it.infantPassenger
            }

            ticketViewModel.passenger.observe(viewLifecycleOwner) {
                passengerList = it.size
            }

            detailsInformationViewModel.gender.observe(viewLifecycleOwner) {
                gender = it.name
            }

            if (passengerAmount == passengerList && fullName.isNotEmpty() && phone.isNotEmpty() && email.contains("@", ignoreCase = true) && gender.isNotEmpty()) {
                val destination =
                    DetailsInformationFragmentDirections.actionDetailsInformationFragmentToTravelAddOnsFragment()

                ticketViewModel.setBuyerContact(
                    fullName = fullName,
                    genderType = gender,
                    email = email,
                    phoneNumber = phone
                )

                findNavController().safeNavigate(destination)

            } else if (gender.isEmpty())
                Toast.makeText(activity, "We need your gender", Toast.LENGTH_SHORT).show()
            else if (fullName.isEmpty())
                Toast.makeText(activity, "We need your name", Toast.LENGTH_SHORT).show()
            else if (phone.isEmpty())
                Toast.makeText(activity, "We need your phone", Toast.LENGTH_SHORT).show()
            else if (!email.contains("@", ignoreCase = true))
                Toast.makeText(activity, "We need your email", Toast.LENGTH_SHORT).show()
            else if (passengerAmount != passengerList)
                Toast.makeText(activity, "Passenger is still empty", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(activity, "Please fill the information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCheckboxGenderAndId() {

        binding.rgGender.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.radio_button_mr ->
                    detailsInformationViewModel.setGenderAndId(
                        gender = GenderEnum.MAN,
                        id = CourtesyEnum.Mr
                    )

                R.id.radio_button_mrs ->
                    detailsInformationViewModel.setGenderAndId(
                        gender = GenderEnum.WOMAN,
                        id = CourtesyEnum.Mrs
                    )

                R.id.radio_button_ms ->
                    detailsInformationViewModel.setGenderAndId(
                        gender = GenderEnum.WOMAN,
                        id = CourtesyEnum.Ms
                    )
            }
        }
    }

    private fun onBtnSwitchClicked(passengerInput: PassengerInputModel) {

        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            val fullName = binding.etFullName.text.toString()
            var gender = ""
            var checkBoxId = CourtesyEnum.NULL

            detailsInformationViewModel.gender.observe(viewLifecycleOwner) {
                gender = it.name
            }

            detailsInformationViewModel.checkBoxId.observe(viewLifecycleOwner) {
                checkBoxId = it
            }

            when (isChecked) {
                true -> {
                    if (checkFirstPassenger(fullName, gender))
                        ticketViewModel.setPassenger(
                            id = passengerInput.id,
                            fullName = fullName,
                            ageType = passengerInput.age,
                            checkBoxId = checkBoxId,
                            genderType = gender
                        )
                }

                false -> {
                    if (checkFirstPassenger(fullName, gender))
                        ticketViewModel.deleteMatchingPassenger(
                            id = passengerInput.id,
                            fullName = fullName,
                            age = passengerInput.age,
                            checkBoxId = checkBoxId,
                            gender = gender
                        )
                }
            }
        }
    }

    private fun checkFirstPassenger(
        fullName: String,
        gender: String
    ): Boolean =
        fullName.isNotEmpty() && gender.isNotEmpty()


    private fun onFirstPassengerClicked(input: PassengerInputModel) {
        binding.rlFirstPassenger.setOnClickListener {
            navigateToBottomSheet(input)
        }
    }

    private fun populateView() {

        ticketViewModel.departureTicket.observe(viewLifecycleOwner) { departure ->
            Timber.d("populateView $departure")

            binding.departureDate.text =
                DateFormat.formatDateStringToAbbreviatedString(departure.departureDate)

            binding.tvDepartureCityCodeLabel.text = departure.sourceAirport.cityCode
            binding.tvArriveCityCodeLabel.text = departure.destinationAirport.cityCode

            binding.directFlight.text =
                getString(R.string.tv_direct_flight, departure.departureTime, departure.arrivalTime)
            binding.ticketType.text = AppUtils.capitalize(departure.classType)
        }
    }

    private fun populateFirstPassengerView(firstPassenger: PassengerInputModel) {
        val passengerAge = firstPassenger.age.name.lowercase().capitalize()

        binding.tvPassengerDetail.text =
            getString(R.string.tv_passenger_detail, firstPassenger.id, passengerAge)
    }

    private fun createPassengerDetailInput() {
        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { preferableTrip ->
            detailsInformationViewModel.createPassengerInput(
                adult = preferableTrip.adultPassenger,
                child = preferableTrip.childPassenger,
                infant = preferableTrip.infantPassenger
            ).observe(viewLifecycleOwner) { passenger ->
                when (passenger) {
                    is CommonResult.Error -> {}

                    is CommonResult.Success -> {
                        val recyclerView = binding.rvPassengerInput
                        val snapHelper = PagerSnapHelper()
                        val _adapter = PassengerInputAdapter()
                        val _layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        val passengerData = passenger.data.toMutableList()
                        val firstPassengerData = passengerData[0]

                        snapHelper.attachToRecyclerView(recyclerView)


                        populateFirstPassengerView(firstPassengerData)
                        onFirstPassengerClicked(firstPassengerData)
                        onBtnSwitchClicked(firstPassengerData)

                        _adapter.apply {
                            passengerData.removeAt(0)
                            submitList(passengerData)
                        }

                        recyclerView.apply {
                            adapter = _adapter
                            layoutManager = _layoutManager
                            setHasFixedSize(true)
                        }

                        _adapter.adapterPassengerInputOnClick()
                    }

                }
            }
        }
    }

    private fun PassengerInputAdapter.adapterPassengerInputOnClick() {
        this.setOnInputClickCallback(object : PassengerInputAdapter.OnInputClickCallback {
            override fun onInputClickCallback(input: PassengerInputModel) {
                navigateToBottomSheet(input)
            }

        })
    }

    private fun navigateToBottomSheet(input: PassengerInputModel) {
        val destination =
            DetailsInformationFragmentDirections
                .actionDetailsInformationFragmentToPassengerDetailsBottomSheetDialog(
                    input
                )

        findNavController().safeNavigate(destination)

    }

}
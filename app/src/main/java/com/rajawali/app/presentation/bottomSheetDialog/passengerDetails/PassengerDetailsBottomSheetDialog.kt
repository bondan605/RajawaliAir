package com.rajawali.app.presentation.bottomSheetDialog.passengerDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.BottomSheetDialogPassengerDetailsBinding
import com.rajawali.app.presentation.bottomSheetDialog.flightDetail.FlightDetailBottomSheetViewModel
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.core.domain.enums.CourtesyEnum
import com.rajawali.core.domain.enums.GenderEnum
import com.rajawali.core.domain.model.PassengerInputModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PassengerDetailsBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogPassengerDetailsBinding get() = _binding!!
    private var _binding: BottomSheetDialogPassengerDetailsBinding? = null

//    private val ticketViewModel: TicketViewModel by activityViewModels()
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val viewModel: FlightDetailBottomSheetViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            BottomSheetDialogPassengerDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here.

        populateView()

        onBtnSaveClicked()

        onButtonDismissClicked()

        getCheckboxGenderAndId()
    }

    private fun onButtonDismissClicked() {
        binding.btnExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onBtnSaveClicked() {
        binding.btnSave.setOnClickListener {
            savePassengerInfo()
        }
    }

    private fun savePassengerInfo() {
        val passengerInput: PassengerInputModel = getPassengerInputBundle()
        val fullName = binding.etName.text.toString()
        var gender = ""
        var checkBoxId = CourtesyEnum.NULL

        viewModel.gender.observe(viewLifecycleOwner) {
            gender = it.name
        }

        viewModel.checkBoxId.observe(viewLifecycleOwner) {
            checkBoxId = it
        }

        if (gender.isNotEmpty() && fullName.isNotEmpty()) {
            ticketViewModel.setPassenger(
                id = passengerInput.id,
                ageType = passengerInput.age,
                fullName = fullName,
                genderType = gender,
                checkBoxId = checkBoxId
            )

            findNavController().popBackStack()
        } else if (gender.isEmpty())
            Toast.makeText(activity, "Gender is not yet chosen", Toast.LENGTH_SHORT).show()
        else if (fullName.isEmpty())
            Toast.makeText(activity, "Name is empty", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(activity, "Name and Gender is empty", Toast.LENGTH_SHORT).show()
    }

    private fun getCheckboxGenderAndId() {

        binding.rgGender.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.rb_mr ->
                    viewModel.setGenderAndId(
                        gender = GenderEnum.MAN,
                        id = CourtesyEnum.Mr
                    )

                R.id.rb_mrs ->
                    viewModel.setGenderAndId(
                        gender = GenderEnum.WOMAN,
                        id = CourtesyEnum.Mrs
                    )

                R.id.rb_ms ->
                    viewModel.setGenderAndId(
                        gender = GenderEnum.WOMAN,
                        id = CourtesyEnum.Ms
                    )
            }
        }
    }

    private fun populateView() {
        val passengerInput: PassengerInputModel = getPassengerInputBundle()

        ticketViewModel.passenger.observe(viewLifecycleOwner) { passengerList ->
            Timber.d("populateViewPassengerDetails : $passengerList")

            passengerList.map { passenger ->

                if (passenger.id == passengerInput.id) {

                    CoroutineScope(Dispatchers.Main).launch {
                        binding.etName.setText(passenger.fullName)
                    }

                    setCheckBoxView(passenger.checkBoxId)

                }
            }
        }
    }

    private fun setCheckBoxView(id: CourtesyEnum) {

        Timber.d("setCheckBoxView $id")

        when (id) {
            CourtesyEnum.Mr ->
                binding.rbMr.isChecked = true

            CourtesyEnum.Mrs ->
                binding.rbMrs.isChecked = true

            CourtesyEnum.Ms ->
                binding.rbMs.isChecked = true

            CourtesyEnum.NULL -> {}
        }
    }

    private fun getPassengerInputBundle(): PassengerInputModel =
        PassengerDetailsBottomSheetDialogArgs.fromBundle(arguments as Bundle).passenger
}

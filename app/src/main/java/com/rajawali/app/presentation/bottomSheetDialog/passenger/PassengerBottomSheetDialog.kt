package com.rajawali.app.presentation.bottomSheetDialog.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.R
import com.rajawali.app.databinding.BottomSheetDialogPassengerBinding
import com.rajawali.app.util.AppUtils
import com.rajawali.core.domain.enums.ModifyCountTypeEnum
import com.rajawali.core.domain.enums.PassengerCategoryEnum
import com.rajawali.core.domain.enums.PassengerClassEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassengerBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogPassengerBinding get() = _binding!!
    private var _binding: BottomSheetDialogPassengerBinding? = null

//    private val passengerViewModel: PassengerViewModel by activityViewModels()
    private val passengerViewModel: PassengerViewModel by navGraphViewModels(R.id.nav_booking)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogPassengerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here.

        dismissBottomSheet()

        //stopping using from writing isBlank / isEmpty
        configurePassengerCountEditTexts()

        //add button function for adding passenger and remove passenger.
        setOnAdultButtonClickListener()
        setOnChildButtonClickListener()
        setOnInfantButtonClickListener()

        //change the edit text number in between plus and minus button
        updateUIBasedOnPassengerCount()


        onButtonDoneClickListener()

        //passenger class
        handlePassengerClassClicks()
        updateUIBasedOnPassengerClass()

    }


    private fun handlePassengerClassClicks() {
        val economyClass = binding.clClassEconomy
        val businessClass = binding.clClassBusiness
        val firstClass = binding.clClassFirst

        economyClass.setOnClickListener {
            passengerViewModel.modifyPassengerClass(PassengerClassEnum.ECONOMY)
        }

        businessClass.setOnClickListener {
            passengerViewModel.modifyPassengerClass(PassengerClassEnum.BUSINESS)
        }

        firstClass.setOnClickListener {
            passengerViewModel.modifyPassengerClass(PassengerClassEnum.FIRST)
        }
    }

    private fun updateUIBasedOnPassengerClass() {
        passengerViewModel.passengerClass.observe(viewLifecycleOwner) { passengerClass ->

            when (passengerClass) {
                PassengerClassEnum.ECONOMY ->
                    highlightSelectedPassengerClass(
                        economyClass = true,
                        businessClass = false,
                        firstClass = false
                    )

                PassengerClassEnum.BUSINESS ->
                    highlightSelectedPassengerClass(
                        economyClass = false,
                        businessClass = true,
                        firstClass = false
                    )

                PassengerClassEnum.FIRST ->
                    highlightSelectedPassengerClass(
                        economyClass = false,
                        businessClass = false,
                        firstClass = true
                    )

                else ->
                    highlightSelectedPassengerClass(
                        economyClass = false,
                        businessClass = false,
                        firstClass = false
                    )
            }
        }
    }

    //highlight by changing constraint layout background when selected class is clicked.
    private fun highlightSelectedPassengerClass(
        economyClass: Boolean,
        businessClass: Boolean,
        firstClass: Boolean
    ) {
        binding.clClassEconomy.modifyPassengerClassBackground(economyClass)
        binding.clClassBusiness.modifyPassengerClassBackground(businessClass)
        binding.clClassFirst.modifyPassengerClassBackground(firstClass)

        binding.ivEconomyCheck.modifyPassengerIcon(economyClass)
        binding.ivBusinessCheck.modifyPassengerIcon(businessClass)
        binding.ivFirstCheck.modifyPassengerIcon(firstClass)
    }

    //change the constraint layout background
    private fun ConstraintLayout.modifyPassengerClassBackground(isChecked: Boolean) {
        this.background = when (isChecked) {
            true ->
                ContextCompat.getDrawable(
                    requireActivity(),
                    com.rajawali.core.R.drawable.custom_cl_class_true
                )

            false ->
                ContextCompat.getDrawable(
                    requireActivity(),
                    com.rajawali.core.R.drawable.custom_cl_class_false
                )
        }
    }

    //change the image view inside constraint layout passenger class visibility
    private fun ImageView.modifyPassengerIcon(isChecked: Boolean) {
        val visible = View.VISIBLE
        val gone = View.GONE

        this.visibility = when (isChecked) {
            true ->
                visible

            false ->
                gone
        }
    }

    private fun onButtonDoneClickListener() {
        binding.btnDone.setOnClickListener {
            val adultPassenger = binding.includeAdultPassenger.etPassenger.text.toString()
            val childPassenger = binding.includeChildPassenger.etPassenger.text.toString()
            val infantPassenger = binding.includeInfantPassenger.etPassenger.text.toString()

            val extractedAdultString = AppUtils.getNumberFromString(adultPassenger)
            val extractedChildString = AppUtils.getNumberFromString(childPassenger)
            val extractedInfantString = AppUtils.getNumberFromString(infantPassenger)

            val adultNumber = AppUtils.isNumberExist(extractedAdultString)
            val childNumber = AppUtils.isNumberExist(extractedChildString)
            val infantNumber = AppUtils.isNumberExist(extractedInfantString)

            passengerViewModel.modifyPassengerCount(
                adultNumber,
                ModifyCountTypeEnum.STRAIGHT,
                PassengerCategoryEnum.ADULT
            )

            passengerViewModel.modifyPassengerCount(
                childNumber,
                ModifyCountTypeEnum.STRAIGHT,
                PassengerCategoryEnum.CHILD
            )

            passengerViewModel.modifyPassengerCount(
                infantNumber,
                ModifyCountTypeEnum.STRAIGHT,
                PassengerCategoryEnum.INFANT
            )

            passengerViewModel.setOnButtonDoneClickListener(true)

            findNavController().popBackStack()
        }
    }

    private fun dismissBottomSheet() {
        binding.btnExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun configurePassengerCountEditTexts() {
        binding.includeChildPassenger.etPassenger.setDefaultPassengerCountIfEmpty()
        binding.includeAdultPassenger.etPassenger.setDefaultPassengerCountIfEmpty()
        binding.includeInfantPassenger.etPassenger.setDefaultPassengerCountIfEmpty()
    }

    private fun EditText.setDefaultPassengerCountIfEmpty() {
        val editText = this
        editText.doAfterTextChanged {
            if (it.toString().isEmpty())
                CoroutineScope(Dispatchers.Main).launch {
                    editText.setText("0")
                }
        }
    }


    private fun updateUIBasedOnPassengerCount() {
        passengerViewModel.adultPassengerCount.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.includeAdultPassenger.etPassenger.setText(it.toString())
            }
        }

        passengerViewModel.childPassengerCount.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.includeChildPassenger.etPassenger.setText(it.toString())
            }
        }
        passengerViewModel.infantPassengerCount.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.includeInfantPassenger.etPassenger.setText(it.toString())
            }
        }
    }

    private fun setOnAdultButtonClickListener() {
        val include = binding.includeAdultPassenger

        include.ibAdd.modifyPassengerCount(
            ModifyCountTypeEnum.ADD,
            PassengerCategoryEnum.ADULT
        )

        include.ibRemove.modifyPassengerCount(
            ModifyCountTypeEnum.REMOVE,
            PassengerCategoryEnum.ADULT
        )
    }

    private fun setOnChildButtonClickListener() {
        val include = binding.includeChildPassenger

        include.ibAdd.modifyPassengerCount(
            ModifyCountTypeEnum.ADD,
            PassengerCategoryEnum.CHILD
        )

        include.ibRemove.modifyPassengerCount(
            ModifyCountTypeEnum.REMOVE,
            PassengerCategoryEnum.CHILD
        )
    }

    private fun setOnInfantButtonClickListener() {
        val include = binding.includeInfantPassenger

        include.ibAdd.modifyPassengerCount(
            ModifyCountTypeEnum.ADD,
            PassengerCategoryEnum.INFANT
        )

        include.ibRemove.modifyPassengerCount(
            ModifyCountTypeEnum.REMOVE,
            PassengerCategoryEnum.INFANT
        )
    }

    private fun ImageButton.modifyPassengerCount(
        modifyType: ModifyCountTypeEnum,
        passengerCategoryEnum: PassengerCategoryEnum,
        count: Int = 0,
    ) {
        this.setOnClickListener {
            passengerViewModel.modifyPassengerCount(count, modifyType, passengerCategoryEnum)
        }
    }

}
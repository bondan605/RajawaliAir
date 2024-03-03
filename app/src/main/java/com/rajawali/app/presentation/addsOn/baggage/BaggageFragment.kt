package com.rajawali.app.presentation.addsOn.baggage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentBaggageBinding
import com.rajawali.app.presentation.chooseTicket.TicketViewModel
import com.rajawali.app.presentation.detailsInformation.DetailsInformationViewModel
import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.result.CommonResult
import com.rajawali.core.presentation.adapter.BaggageAdapter
import com.rajawali.core.presentation.viewModel.TravelAddsOnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaggageFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentBaggageBinding? = null

//    private val addsOnViewModel: TravelAddsOnViewModel by activityViewModels()
    private val addsOnViewModel: TravelAddsOnViewModel by navGraphViewModels(R.id.nav_booking)
    private val ticketViewModel: TicketViewModel by navGraphViewModels(R.id.nav_booking)
    private val passenger: DetailsInformationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaggageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

        createPassengerDetailInput()

        setTotalPrice()

        dismiss()
    }

    private fun dismiss() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTotalPrice() {
        addsOnViewModel.passengerBaggageList.observe(viewLifecycleOwner) { passengersBaggage ->
            addsOnViewModel.setTotalBaggagePrice(passengersBaggage)
        }

        addsOnViewModel.totalBaggagePrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.totalPrice.text = getString(com.rajawali.common_resource.R.string.tv_total_price, totalPrice)
        }
    }

    private fun createPassengerDetailInput() {
        ticketViewModel.preferableDeparture.observe(viewLifecycleOwner) { preferableTrip ->
            passenger.createPassengerInput(
                adult = preferableTrip.adultPassenger,
                child = preferableTrip.childPassenger,
                infant = preferableTrip.infantPassenger
            ).observe(viewLifecycleOwner) { passenger ->
                when (passenger) {
                    is CommonResult.Error -> {}

                    is CommonResult.Success -> {
                        val recyclerView = binding.rvPassengersBaggage
//                        val snapHelper = PagerSnapHelper()
                        val _adapter = BaggageAdapter(addsOnViewModel)
                        val _layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )

//                        snapHelper.attachToRecyclerView(recyclerView)

                        _adapter.apply {
                            submitList(passenger.data)
                        }

                        recyclerView.apply {
                            adapter = _adapter
                            layoutManager = _layoutManager
                            setHasFixedSize(true)
                        }

                        _adapter.onPassengerBaggageClicked()
                    }
                }
            }
        }
    }

    private fun BaggageAdapter.onPassengerBaggageClicked() {
        this.setOnBaggageClickCallback(object : BaggageAdapter.OnBaggageClickCallback {
            override fun onBaggageClickCallback(id: Int, baggage: BaggageEnum) {
                addsOnViewModel.setBaggageList(id, baggage)
            }
        })
    }

}
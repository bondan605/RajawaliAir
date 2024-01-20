package com.rajawali.app.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentOneWayTripBinding
import com.rajawali.app.presentation.pickCity.AirportsViewModel

class OneWayTripFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentOneWayTripBinding? = null

    private val airportsViewModel : AirportsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneWayTripBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

    }

    private fun setDeparture() {
        airportsViewModel.arrivingAirport.observe(viewLifecycleOwner) {
        }
    }
}
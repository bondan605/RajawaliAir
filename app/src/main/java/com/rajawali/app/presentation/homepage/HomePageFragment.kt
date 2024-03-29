package com.rajawali.app.presentation.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentHomePageBinding
import com.rajawali.app.presentation.pickCity.AirportsViewModel
import com.rajawali.app.util.NavigationUtils.safeNavigate
import com.rajawali.core.domain.enums.AirportTypeEnum
import com.rajawali.core.domain.enums.NotAvailableEnum
import com.rajawali.core.domain.model.PromotionList
import com.rajawali.core.domain.model.TouristDestinationList
import com.rajawali.core.presentation.adapter.PromotionAdapter
import com.rajawali.core.presentation.adapter.TouristDestinationAdapter
import com.rajawali.core.presentation.customView.setPreviewBothSide
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomePageFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentHomePageBinding? = null

    private val airportsViewModel: AirportsViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)

        //work wonderful. setting the status bar
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.rajawali.common_resource.R.color.dodger_blue)
        val windowInsetController = WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        )
        windowInsetController.isAppearanceLightStatusBars = false

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //do something here.

        promotionViewPager()

        touristDestinationRecyclerview()

        btnBookNow()

        setDeparture()
        setArriving()

        setOnNotificationClicked()

        setOnBottomNavigationClicked()
    }

    private fun setOnBottomNavigationClicked() {
        binding.includeBottomNavigation.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    true
                }

                R.id.menu_bookings -> {
                    navigate(HomePageFragmentDirections.actionHomePageFragmentToOneWayTripFragment())
                    true
                }

                R.id.menu_account -> {
                    navigate(HomePageFragmentDirections.actionHomePageFragmentToNotLoginSheetDialog(NotAvailableEnum.IMPLEMENT))
                    true
                }

                R.id.menu_history -> {
                    navigate(HomePageFragmentDirections.actionHomePageFragmentToNotLoginSheetDialog(NotAvailableEnum.IMPLEMENT))
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun navigate(destination: NavDirections) {
        findNavController().safeNavigate(destination)
    }

    private fun setOnNotificationClicked() {
        binding.includeToolbar.ibNotification.setOnClickListener {
            val destination =
                HomePageFragmentDirections.actionHomePageFragmentToNotificationFragment()

            findNavController().safeNavigate(destination)
        }
    }

    private fun promotionViewPager() {
        val vpPromotion = binding.vpPromotion
        val _adapter = PromotionAdapter()
        val pageIndicator = binding.diPromotionPageIndicator

        _adapter.submitList(PromotionList.promotions)
        vpPromotion.apply {
            adapter = _adapter
            setPreviewBothSide(
                com.rajawali.common_resource.R.dimen.vp_next_item_visible_size,
                com.rajawali.common_resource.R.dimen.vp_current_item_horizontal_margin
            )
        }

        pageIndicator.attachTo(vpPromotion)
    }

    private fun touristDestinationRecyclerview() {

        val rvPromotion = binding.rvTouristDestination
        val snapHelper = PagerSnapHelper()
        val _adapter = TouristDestinationAdapter()
        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

//        sticky when moving the recyclerview
        snapHelper.attachToRecyclerView(rvPromotion)

        _adapter.submitList(TouristDestinationList.destination)
        rvPromotion.apply {
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }
    }

    private fun btnBookNow() {
        binding.btnBookNow.setOnClickListener {
            val destination =
                HomePageFragmentDirections.actionHomePageFragmentToOneWayTripFragment()
            findNavController().safeNavigate(destination)
        }
    }

    private fun setDeparture() {
        /*  alternative way
                val pickCityBottomSheet = PickCityBottomSheetDialog()
                pickCityBottomSheet.show(requireActivity().supportFragmentManager, PickCityBottomSheetDialog.TAG)
        */
        binding.clDeparture.setOnClickListener {
            val destination =
                HomePageFragmentDirections.actionHomePageFragmentToAirportBottomSheetDialog(
                    AirportTypeEnum.DEPARTURE
                )
            findNavController().safeNavigate(destination)
        }

        airportsViewModel.departureAirport.observe(viewLifecycleOwner) { departure ->
            binding.tvDepartureCityCodeLabel.text = departure.cityCode
            binding.tvDepartureCityNameLabel.text =
                getString(com.rajawali.common_resource.R.string.tv_departure_city_name_label, departure.city, departure.cityCode)
        }
    }

    private fun setArriving() {
        binding.clArrive.setOnClickListener {
            val destination =
                HomePageFragmentDirections.actionHomePageFragmentToAirportBottomSheetDialog(
                    AirportTypeEnum.ARRIVING
                )
            findNavController().safeNavigate(destination)
        }

        airportsViewModel.arrivingAirport.observe(viewLifecycleOwner) { arriving ->

            binding.tvArriveCityCodeLabel.text = arriving.cityCode
            binding.tvArriveCityNameLabel.text =
                getString(com.rajawali.common_resource.R.string.tv_departure_city_name_label, arriving.city, arriving.cityCode)
        }

    }
}
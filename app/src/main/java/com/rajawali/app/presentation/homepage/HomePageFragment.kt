package com.rajawali.app.presentation.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.rajawali.app.R
import com.rajawali.app.databinding.FragmentHomePageBinding
import com.rajawali.app.presentation.pickCity.PickCityFragment
import com.rajawali.core.domain.model.PromotionList
import com.rajawali.core.domain.model.TouristDestinationList
import com.rajawali.core.presentation.adapter.PromotionAdapter
import com.rajawali.core.presentation.adapter.TouristDestinationAdapter
import com.rajawali.core.presentation.customView.setPreviewBothSide

class HomePageFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentHomePageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //change app notification color background / android:windowBackground
//        activity?.window?.setBackgroundDrawableResource(R.color.main_color)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
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
    }

    private fun promotionViewPager() {
        val vpPromotion = binding.vpPromotion
        val _adapter = PromotionAdapter()
        val pageIndicator = binding.diPromotionPageIndicator

        _adapter.submitList(PromotionList.promotions)
        vpPromotion.apply {
            adapter = _adapter
            setPreviewBothSide(
                R.dimen.vp_next_item_visible_size,
                R.dimen.vp_current_item_horizontal_margin
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
            val pickCityBottomSheet = PickCityFragment()

            pickCityBottomSheet.show(requireActivity().supportFragmentManager, PickCityFragment.TAG)


        }
    }
}
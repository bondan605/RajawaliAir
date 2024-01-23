package com.rajawali.app.presentation.pickCity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.databinding.BottomSheetDialogPickCityBinding
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.presentation.adapter.SearchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PickCityBottomSheetDialog : BottomSheetDialogFragment() {
    private val binding: BottomSheetDialogPickCityBinding get() = _binding!!
    private var _binding: BottomSheetDialogPickCityBinding? = null

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogPickCityBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something here.

        whenSearching()
        dismissBottomSheet()


        recentSearch()
    }

    private fun searchResult(keyword: String) {
        val recyclerview = binding.rvSearchResult
        val _adapter = SearchAdapter()
        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)


        _adapter.setOnAirportClickCallback(object : SearchAdapter.OnAirportClickCallback {
            override fun onAirportClickCallback(airport: SearchModel) {

            }
        })

        viewModel.getSearchedAirport(keyword).observe(viewLifecycleOwner) {
            //need to add loading animation.

            when (it) {
                is UCResult.Success -> {
                    _adapter.submitList(it.data)
                }

                is UCResult.Error -> {
                    Toast.makeText(activity, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


        recyclerview.apply {
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }
    }

    private fun recentSearch() {
        val recyclerview = binding.rvRecentSearch
        val _adapter = SearchAdapter()
        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

//        viewModel.getRecentSearch.observe(viewLifecycleOwner) {
//            when (it) {
//                is UCResult.Success -> {
//                    _adapter.submitList(it.data)
//                }
//
////                TODO need to add response when data is empty
//                is UCResult.Error -> {}
//            }
//
//        }

        recyclerview.apply {
            adapter = _adapter
            layoutManager = _layoutManager
            setHasFixedSize(true)
        }
    }

    private fun whenSearching() {
        val etSearch = binding.etSearch
        val tvRecentSearchLabel = binding.tvRecentSearch
        val btnClearRecentSearch = binding.btnClearRecentSearch
        val rvSearchResult = binding.rvSearchResult
        val rvRecentSearch = binding.rvRecentSearch

        val visible = View.VISIBLE
        val gone = View.GONE

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val textLength = text?.length ?: 0

                if (textLength < 1) {
                    tvRecentSearchLabel.visibility = visible
                    btnClearRecentSearch.visibility = visible
                    rvRecentSearch.visibility = visible

                    rvSearchResult.visibility = gone
                } else {
                    //search with keyword
                    searchResult(text.toString())

                    tvRecentSearchLabel.visibility = gone
                    btnClearRecentSearch.visibility = gone
                    rvRecentSearch.visibility = gone

                    rvSearchResult.visibility = visible
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        }
        )
    }

    private fun dismissBottomSheet() {
        binding.btnExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
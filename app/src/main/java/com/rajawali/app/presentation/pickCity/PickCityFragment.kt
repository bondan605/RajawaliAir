package com.rajawali.app.presentation.pickCity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rajawali.app.databinding.FragmentPickCityBinding
import com.rajawali.core.domain.model.SearchList
import com.rajawali.core.presentation.adapter.SearchAdapter

class PickCityFragment : BottomSheetDialogFragment() {
    private val binding: FragmentPickCityBinding get() = _binding!!
    private var _binding: FragmentPickCityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickCityBinding.inflate(layoutInflater, container, false)

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

        searchResult()
        recentSearch()
    }

    private fun searchResult() {
        val recyclerview = binding.rvSearchResult
        val _adapter = SearchAdapter()
        val _layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        _adapter.submitList(SearchList.searchResult)
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

        _adapter.submitList(SearchList.recentSearch)
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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                val textLength = text?.length ?: 0

                if (textLength < 1) {
                    tvRecentSearchLabel.visibility = View.VISIBLE
                    btnClearRecentSearch.visibility = View.VISIBLE
                    rvRecentSearch.visibility = View.VISIBLE

                    rvSearchResult.visibility = View.GONE
                } else {
                    tvRecentSearchLabel.visibility = View.GONE
                    btnClearRecentSearch.visibility = View.GONE
                    rvRecentSearch.visibility = View.GONE

                    rvSearchResult.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
//                TODO("Not yet implemented")
            }

        }
        )
    }

    private fun dismissBottomSheet() {
        binding.btnExit.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "PickCityBottomSheet"
    }
}
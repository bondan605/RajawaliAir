package com.rajawali.app.presentation.pickCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.result.UCResult
import com.rajawali.core.domain.usecase.AddRecentSearchUseCase
import com.rajawali.core.domain.usecase.GetAirportsUseCase
import com.rajawali.core.domain.usecase.GetRecentSearchUseCase


class SearchViewModel(
//    getRecentSearchUC: GetRecentSearchUseCase,
//    private val addSearchUC: AddRecentSearchUseCase,
    private val getAirportsUC: GetAirportsUseCase
) : ViewModel() {

//    val getRecentSearch = getRecentSearchUC.getRecentSearch().asLiveData()

    fun getSearchedAirport(keyword: String): LiveData<UCResult<List<SearchModel>>> =
        getAirportsUC.getSearchedAirport(keyword).asLiveData()


//    fun addSearch(search: SearchModel) =
//        addSearchUC.addRecentSearch(search)

}
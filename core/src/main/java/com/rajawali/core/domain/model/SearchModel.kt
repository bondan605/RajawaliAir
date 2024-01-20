package com.rajawali.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchModel(
    val id : String,
    val city : String,
    val cityCode : String,
    val airport : String
) : Parcelable

object SearchList {
    val searchResult = listOf(
        SearchModel(
            id = "1",
            city = "Yogyakarta, Indonesia",
            airport = "YIA -Yogyakarta Kulon Progo",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "2",
            city = "Yogyakarta, Indonesia",
            airport = "JOG - Adi Sutjipto",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "3",
            city = "Praya, Indonesia",
            airport = "LOP - Lombok",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "4",
            city = "Jayapura, Indonesia",
            airport = "DJJ - Sentani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "5",
            city = "Surabaya, Indonesia",
            airport = "SUB - Juanda",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "8",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "9",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "10",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "11",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "12",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "13",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "14",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "15",
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
            cityCode = "YIA"
        ),

        )
    val recentSearch = listOf(
        SearchModel(
            id = "1",
            city = "Bali / Denpasar, Indonesia",
            airport = "DPS - Ngurah Rai International Airport",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "2",
            city = "Balikpapan, Indonesia",
            airport = "BPN - Sepinggan",
            cityCode = "YIA"
        ),
        SearchModel(
            id = "3",
            city = "Yogyakarta, Indonesia",
            airport = "YIA -Yogyakarta Kulon Progo",
            cityCode = "YIA"
        ),
    )
}

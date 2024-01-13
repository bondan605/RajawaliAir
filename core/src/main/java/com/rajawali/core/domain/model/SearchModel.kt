package com.rajawali.core.domain.model

data class SearchModel(
    val id : Int,
    val city : String,
    val airport : String
)

object SearchList {
    val searchResult = listOf(
        SearchModel(
            id = 1,
            city = "Yogyakarta, Indonesia",
            airport = "YIA -Yogyakarta Kulon Progo",
        ),
        SearchModel(
            id = 2,
            city = "Yogyakarta, Indonesia",
            airport = "JOG - Adi Sutjipto",
        ),
        SearchModel(
            id = 3,
            city = "Praya, Indonesia",
            airport = "LOP - Lombok",
        ),
        SearchModel(
            id = 4,
            city = "Jayapura, Indonesia",
            airport = "DJJ - Sentani",
        ),
        SearchModel(
            id = 5,
            city = "Surabaya, Indonesia",
            airport = "SUB - Juanda",
        ),
        SearchModel(
            id = 8,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 9,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 10,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 11,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 12,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 13,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 14,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),
        SearchModel(
            id = 15,
            city = "Semarang, Indonesia",
            airport = "SRG - Achmad Yani",
        ),

        )
    val recentSearch = listOf(
        SearchModel(
            id = 1,
            city = "Bali / Denpasar, Indonesia",
            airport = "DPS - Ngurah Rai International Airport",
        ),
        SearchModel(
            id = 2,
            city = "Balikpapan, Indonesia",
            airport = "BPN - Sepinggan",
        ),
        SearchModel(
            id = 3,
            city = "Yogyakarta, Indonesia",
            airport = "YIA -Yogyakarta Kulon Progo",
        ),
    )
}

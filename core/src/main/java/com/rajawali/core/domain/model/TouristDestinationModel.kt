package com.rajawali.core.domain.model

data class TouristDestinationModel(
    val id: Int,
    val title: String,
    val route: String,
    val price: String
)

object TouristDestinationList {
    val destination = listOf(
        TouristDestinationModel(
            id = 1,
            title = "Bali",
            route = "Jakarta (CGK) - Bali (DPS)",
            price = "IDR 540.000,-",
        ),
        TouristDestinationModel(
            id = 2,
            title = "Japan",
            route = "Jakarta (CGK) - Akita (AXT)",
            price = "IDR 14.634.000,-",
        ),
        TouristDestinationModel(
            id = 3,
            title = "Labuan Bajo",
            route = "Yogyakarta (YIA) - Labuan Bajo (LB)",
            price = "IDR 1.663.000,-",
        ),
        TouristDestinationModel(
            id = 4,
            title = "Thailand",
            route = "Jakarta (CGK) - Bangkok (BKK)",
            price = "IDR 589.000,-",
        ),
        TouristDestinationModel(
            id = 5,
            title = "Korea",
            route = "Jakarta (CGK) - Seoul (ICN)",
            price = "IDR 6.250.000,-",
        ),
    )
}

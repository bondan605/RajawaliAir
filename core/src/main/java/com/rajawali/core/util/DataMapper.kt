package com.rajawali.core.util

import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.data.remote.response.Airplane
import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.DestinationAirport
import com.rajawali.core.data.remote.response.FinishPaymentData
import com.rajawali.core.data.remote.response.FlightDetailListItem
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.LoginData
import com.rajawali.core.data.remote.response.MealItem
import com.rajawali.core.data.remote.response.MealsAddOnsItem
import com.rajawali.core.data.remote.response.NotificationData
import com.rajawali.core.data.remote.response.NotificationsItem
import com.rajawali.core.data.remote.response.PassengerDetailListItem
import com.rajawali.core.data.remote.response.PassengersItem
import com.rajawali.core.data.remote.response.PayReservationData
import com.rajawali.core.data.remote.response.Reservation
import com.rajawali.core.data.remote.response.ReservationByIdData
import com.rajawali.core.data.remote.response.ReservationData
import com.rajawali.core.data.remote.response.SeatsData
import com.rajawali.core.data.remote.response.SeatsItem
import com.rajawali.core.data.remote.response.SourceAirport
import com.rajawali.core.domain.enums.BaggageEnum
import com.rajawali.core.domain.enums.PaymentStatusEnum
import com.rajawali.core.domain.model.AirplaneModel
import com.rajawali.core.domain.model.AvailableSeatsModel
import com.rajawali.core.domain.model.BookingModel
import com.rajawali.core.domain.model.FinishPaymentModel
import com.rajawali.core.domain.model.FlightModel
import com.rajawali.core.domain.model.LoggedInUserModel
import com.rajawali.core.domain.model.MealModel
import com.rajawali.core.domain.model.NotificationItemModel
import com.rajawali.core.domain.model.NotificationModel
import com.rajawali.core.domain.model.PassengerModel
import com.rajawali.core.domain.model.PaymentRecordModel
import com.rajawali.core.domain.model.ReservationByIdModel
import com.rajawali.core.domain.model.ReservationFlightDetailListModel
import com.rajawali.core.domain.model.ReservationMealsAddOnsModel
import com.rajawali.core.domain.model.ReservationModel
import com.rajawali.core.domain.model.ReservationPassengerDetailListModel
import com.rajawali.core.domain.model.ReservationPassengerModel
import com.rajawali.core.domain.model.SearchModel
import com.rajawali.core.domain.model.SeatsModel

object DataMapper {

    fun finishPaymentDataResponseToFinishPaymentModel(response: FinishPaymentData): FinishPaymentModel =
        FinishPaymentModel(
            receiverNumber = response.receiverNumber ?: "",

            createdAt = response.createdAt ?: "",

            method = response.method ?: "",

            verifiedAt = response.verifiedAt ?: "",

            paidAt = response.paidAt ?: "",

            id = response.id ?: "",

            paymentStatus = response.paymentStatus ?: "",

            updatedAt = response.updatedAt ?: ""
        )

    fun notificationDataResponseToNotificationModel(response: NotificationData): NotificationModel =
        NotificationModel(
            isSeen = response.isSeen ?: false,
            notifications = response.notifications.map {
                notificationItemResponseToNotificationItemModel(
                    it
                )
            }
        )

    private fun notificationItemResponseToNotificationItemModel(response: NotificationsItem): NotificationItemModel =
        NotificationItemModel(
            createdAt = response.createdAt ?: "",
            description = response.description ?: "",
            id = response.id ?: "",
            notificationType = response.notificationType ?: "",
            updatedAt = response.updatedAt ?: ""

        )

    fun loginResponseToLoggedInUserModel(response: LoginData): LoggedInUserModel =
        LoggedInUserModel(
            id = response.id ?: "",
            fullName = response.fullName ?: "",
            email = response.email ?: "",
            phoneNumber = response.phoneNumber ?: "",
            accessToken = response.accessToken ?: "",
            refreshToken = response.refreshToken ?: "",
            type = response.type ?: "",

            )

    fun baggageEnumToInt(baggage: BaggageEnum): String =
        Constant.baggageEnumToStringMap[baggage] ?: "0"

    fun paymentStatusStringToPaymentStatusEnum(status: String): PaymentStatusEnum =
        Constant.statusToEnumMap[status] ?: PaymentStatusEnum.PAYMENT_NULL

    fun reservationByIdResponseToReservationByIdModel(response: ReservationByIdData): ReservationByIdModel =
        ReservationByIdModel(
            id = response.id ?: "",
            paymentStatus = response.paymentStatus ?: "",
            expiredAt = response.expiredAt ?: "",
        )

    fun recentSearchEntityToDomain(entity: RecentSearchEntity): SearchModel =
        SearchModel(
            id = entity.id,
            city = entity.city,
            cityCode = entity.cityCode,
            airport = entity.airport
        )

    fun recentSearchDomainToEntity(domain: SearchModel, created: Long): RecentSearchEntity =
        RecentSearchEntity(
            id = domain.id,
            city = domain.city,
            cityCode = domain.cityCode,
            airport = domain.airport,
            created = created
        )

    fun airportsResponseToSearchDomain(response: ContentItem): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    fun flightsResponseToDomain(
        response: FlightItem,
        departureTime: String,
        arrivalTime: String
    ): FlightModel =
        FlightModel(
            discount = response.discount ?: 0,

            destinationAirport = destinationAirportResponseToSearchDomain(response.destinationAirport),

            arrivalDate = response.arrivalDate ?: "",

            availableSeats = response.availableSeats ?: 0,

            points = response.points ?: 0,

            sourceAirport = sourceAirportResponseToSearchDomain(response.sourceAirport),

            airplane = airplaneResponseToAirplaneDomain(response.airplane),

            id = response.id ?: "",

            departureDate = response.departureDate ?: "",

            classType = response.classType ?: "",

            seatPrice = response.seatPrice ?: 0,

            totalPrice = response.totalPrice ?: 0,

            departureTime = departureTime,

            arrivalTime = arrivalTime,
        )

    fun MealsResponseToMealsDomain(response: MealItem): MealModel =
        MealModel(
            id = response.id ?: "",
            price = response.price ?: 0,
            name = response.name ?: "",
            description = response.description ?: "",
            thumbnailUrl = response.thumbnailUrl ?: ""
        )

    fun availableSeatsResponseToAvailableSeatsModel(response: SeatsData): AvailableSeatsModel =
        AvailableSeatsModel(
            classType = response.classType ?: "",
            seatPerCol = response.seatPerCol ?: 0,
            seats = response.seats.map { seatsItemResponseToSeatsModel(it) },
        )

    fun passengerModelToReservationPassengerModel(model: PassengerModel): ReservationPassengerModel =
        ReservationPassengerModel(
            gender = model.genderType,
            fullName = model.fullName,
            age = model.ageType.name
        )

    fun reservationResponseToReservationModel(response: ReservationData): ReservationModel =
        ReservationModel(
            id = response.id ?: "",
            userId = response.user ?: "",
            promoCode = response.promo ?: "",
            classType = response.classType ?: "",
            gender = response.genderType ?: "",
            fullName = response.fullname ?: "",
            email = response.email ?: "",
            phoneNumber = response.phoneNumber ?: "",
            paymentStatus = response.paymentStatus ?: "",
            passengers = response.passengers.map {
                passengersItemResponseToReservationPassengersItemModel(
                    it
                )
            },
            flightDetailList = response.flightDetailList.map {
                flightDetailListItemResponseToReservationFlightDetailModel(
                    it
                )
            },
            expiredAt = response.expiredAt ?: "",
        )

    fun payReservationDataResponseToPaymentModel(response: PayReservationData): PaymentRecordModel =
        PaymentRecordModel(
            receiverNumber = response.receiverNumber ?: "",
            createdAt = response.createdAt ?: "",
            method = response.method ?: "",
            verifiedAt = response.verifiedAt ?: "",
            reservation = reservationResponseToBookingModel(response.reservation),
            paidAt = response.paidAt ?: "",
            id = response.id ?: "",
            paymentStatus = response.paymentStatus ?: "",
            updatedAt = response.updatedAt ?: "",

            )

    private fun reservationResponseToBookingModel(response: Reservation): BookingModel =
        BookingModel(
            expiredAt = response.expiredAt ?: "",
            phoneNumber = response.phoneNumber ?: "",
            totalPrice = response.totalPrice ?: 0,
            id = response.id ?: "",
            fullName = response.fullname ?: "",
            classType = response.classType ?: "",
            email = response.email ?: "",
            genderType = response.genderType ?: "",
        )

    private fun flightDetailListItemResponseToReservationFlightDetailModel(response: FlightDetailListItem): ReservationFlightDetailListModel =
        ReservationFlightDetailListModel(
            id = response.id ?: "",
            flightId = response.flightId ?: "",
            useTravelAssurance = response.useTravelAssurance ?: false,
            useBaggageAssurance = response.useBagageAssurance ?: false,
            useFlightDelayAssurance = response.useFlightDelayAssurance ?: false,
            seatPrice = response.seatPrice ?: 0,
            totalPrice = response.totalPrice ?: 0,
            passengerDetailList = response.passengerDetailList.map
            {
                passengerDetailListItemResponseToReservationPassengerDetailModel(
                    it
                )
            }
        )

    private fun passengerDetailListItemResponseToReservationPassengerDetailModel(response: PassengerDetailListItem): ReservationPassengerDetailListModel =
        ReservationPassengerDetailListModel(
            seatId = response.seatId ?: "",
            baggageAddOns = response.bagageAddOns ?: 0,
            mealsAddOns = response.mealsAddOns.map {
                mealsAddOnsItemResponseToReservationMealsAddOnsModel(
                    it
                )
            },
        )

    private fun mealsAddOnsItemResponseToReservationMealsAddOnsModel(response: MealsAddOnsItem?): ReservationMealsAddOnsModel =
        ReservationMealsAddOnsModel(
            id = response?.id ?: "",
            name = response?.name ?: "",
            price = response?.price ?: 0,
        )


    private fun passengersItemResponseToReservationPassengersItemModel(response: PassengersItem?): ReservationPassengerModel =
        ReservationPassengerModel(
            age = response?.ageType ?: "",
            fullName = response?.fullname ?: "",
            gender = response?.genderType ?: "",
        )

    private fun seatsItemResponseToSeatsModel(response: SeatsItem): SeatsModel =
        SeatsModel(
            id = response.id ?: "",
            isAvailable = response.isAvailable ?: false,
            seatNo = response.seatNo ?: "",
        )

    private fun destinationAirportResponseToSearchDomain(response: DestinationAirport): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    private fun sourceAirportResponseToSearchDomain(response: SourceAirport): SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )

    private fun airplaneResponseToAirplaneDomain(response: Airplane): AirplaneModel =
        AirplaneModel(
            id = response.id ?: "",
            airplaneCode = response.airplaneCode ?: ""
        )
}
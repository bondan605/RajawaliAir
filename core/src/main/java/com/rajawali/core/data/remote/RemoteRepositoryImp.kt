package com.rajawali.core.data.remote

import android.accounts.NetworkErrorException
import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.response.FinishPaymentData
import com.rajawali.core.data.remote.response.FlightItem
import com.rajawali.core.data.remote.response.LoginData
import com.rajawali.core.data.remote.response.MealItem
import com.rajawali.core.data.remote.response.NotificationData
import com.rajawali.core.data.remote.response.PayReservationData
import com.rajawali.core.data.remote.response.RegisterData
import com.rajawali.core.data.remote.response.ReservationByIdData
import com.rajawali.core.data.remote.response.ReservationData
import com.rajawali.core.data.remote.response.SeatsData
import com.rajawali.core.data.remote.service.RajawaliAirService
import com.rajawali.core.domain.model.CreateReservationModel
import com.rajawali.core.domain.model.LoginModel
import com.rajawali.core.domain.model.PayReservationModel
import com.rajawali.core.domain.model.RegisterModel
import com.rajawali.core.domain.model.VerifyModel
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.util.Constant
import timber.log.Timber

class RemoteRepositoryImp(private val service: RajawaliAirService) : RemoteRepository {

    override suspend fun getAirports(): ApiResponse<List<ContentItem>> {
        val response = service.getAirports(Constant.THOUSAND)

        return when (response.success ?: false) {
            true -> {

                when (response.data?.empty) {
                    true ->
                        ApiResponse.Error(Constant.DATA_EMPTY)

                    false ->
                        ApiResponse.Success(response.data.content)

                    null ->
                        ApiResponse.Error(Constant.DATA_EMPTY)
                }
            }

            false -> {
                ApiResponse.Error(response.message ?: Constant.FETCH_FAILED)
            }
        }
    }

    override suspend fun getPreferredFlights(
        departure: String,
        destination: String,
        adultPassenger: Int,
        childPassenger: Int,
        infantPassenger: Int,
        departureDate: String,
        seatType: String
    ): ApiResponse<List<FlightItem>> {
        return try {
            val response = service.getPreferredFlights(
                departure = departure,
                destination = destination,
                adultPassenger = adultPassenger,
                childPassenger = childPassenger,
                infantPassenger = infantPassenger,
                departureDate = departureDate,
                seatType = seatType,
            )

            Timber.d("getPreferredFlights -> Response: $response")

            when (response.success) {
                true -> {
                    when (response.data.empty) {
                        true ->
                            throw Exception(Constant.DATA_EMPTY)

                        false ->
                            ApiResponse.Success(response.data.content)

                        null -> {
                            throw Exception(Constant.DATA_EMPTY)
                        }
                    }
                }

                false ->
                    throw Exception(Constant.FETCH_FAILED)

                null ->
                    throw Exception(response.message ?: Constant.DATA_EMPTY)

            }

        } catch (e: Exception) {
            Timber.w(e)

            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
    }

    override suspend fun getFlightMeals(): ApiResponse<List<MealItem>> {
        val response = service.getMeals()

        return try {

            when (response.success) {
                true -> {
                    when (response.data.empty) {
                        true ->
                            throw Exception(response.message)

                        false ->
                            ApiResponse.Success(response.data.content)

                        null ->
                            throw Exception(Constant.DATA_EMPTY)
                    }
                }

                false ->
                    throw Exception(response.message)

                null ->
                    throw Exception(Constant.DATA_EMPTY)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
    }

    override suspend fun getFlightAvailableSeats(
        id: String,
        seatType: String
    ): ApiResponse<SeatsData> {
        return try {
            val response = service.getFlightAvailableSeats(id, seatType)

            when (response.success) {
                true -> {
                    ApiResponse.Success(response.data)
                }

                false ->
                    throw Exception(response.message)

                null ->
                    throw Exception(Constant.FETCH_FAILED)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
    }

    override suspend fun createReservation(data: CreateReservationModel): ApiResponse<ReservationData> {
        return try {
            val response = service.createReservation(data)

            when (response.success) {
                true ->
                    if (response.data != null)
                        ApiResponse.Success(response.data)
                    else
                        throw Exception(Constant.DATA_EMPTY)

                false ->
                    throw Exception(response.message)

                null ->
                    throw NetworkErrorException(Constant.FETCH_FAILED)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)

        } catch (e: NetworkErrorException) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)

        }
    }


    override suspend fun payReservation(data: PayReservationModel): ApiResponse<PayReservationData> {
        return try {
            val response = service.payReservation(data)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw NetworkErrorException(Constant.FETCH_FAILED)
            }

        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)

        } catch (e: NetworkErrorException) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }

    }

    override suspend fun reservationById(reservationId: String): ApiResponse<ReservationByIdData> =
        try {
            val response = service.reservationById(reservationId)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw NetworkErrorException(Constant.FETCH_FAILED)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        } catch (e: NetworkErrorException) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }

    override suspend fun login(data: LoginModel): ApiResponse<LoginData> =
        try {
            val response = service.login(data)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw NetworkErrorException(response.message.toString())
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(Constant.ACCOUNT_NOT_FOUND)
        } catch (e: NetworkErrorException) {
            Timber.w(e)
            ApiResponse.Error(Constant.ACCOUNT_NOT_FOUND)
        }

    override suspend fun register(data: RegisterModel): ApiResponse<RegisterData> =
        try {
            val response = service.register(data)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw NetworkErrorException(response.message.toString())
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(Constant.ACCOUNT_NOT_FOUND)
        } catch (e: NetworkErrorException) {
            Timber.w(e)
            ApiResponse.Error(Constant.ACCOUNT_NOT_FOUND)
        }

    override suspend fun verifyNewAccount(data: VerifyModel): ApiResponse<String> =
        try {
            val response = service.verifyNewAccount(data)

            when (response.success) {
                true -> {
                    if (response.message != null)
                        ApiResponse.Success(response.message)
                    else
                        throw Exception()
                }

                false ->
                    throw Exception(response.message)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.OTP_VERIFY_FAILED)
        }

    override suspend fun getNotification(
        id: String,
        accessToken: String
    ): ApiResponse<NotificationData> =
        try {
//            val header = HashMap<String, String>()
//            header["Bearer"] = "Bearer $accessToken"
            val header = "Bearer $accessToken"

            val response = service.getNotification(id, header)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw Exception(Constant.FETCH_FAILED)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }

    override suspend fun finishPayment(id: String): ApiResponse<FinishPaymentData> =
        try {
            val response = service.finishPayment(id)

            when (response.success) {
                true ->
                    ApiResponse.Success(response.data)

                false ->
                    throw Exception(response.message)

                null ->
                    throw Exception(Constant.FETCH_FAILED)
            }
        } catch (e: Exception) {
            Timber.w(e)
            ApiResponse.Error(e.message ?: Constant.FETCH_FAILED)
        }
}
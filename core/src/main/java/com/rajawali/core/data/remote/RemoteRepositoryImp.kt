package com.rajawali.core.data.remote

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.data.remote.service.RajawaliAirService
import com.rajawali.core.domain.repository.RemoteRepository
import com.rajawali.core.domain.result.ApiResponse
import com.rajawali.core.util.Constant

class RemoteRepositoryImp(private val service: RajawaliAirService) : RemoteRepository {

    override suspend fun getAirports(): ApiResponse<List<ContentItem>> {
        val response = service.getAirports()

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
}
package com.rajawali.core.domain.repository

import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.domain.result.ApiResponse

interface RemoteRepository {

    suspend fun getAirports(): ApiResponse<List<ContentItem>>
}
package com.rajawali.core.util

import com.rajawali.core.data.local.room.entity.RecentSearchEntity
import com.rajawali.core.data.remote.response.ContentItem
import com.rajawali.core.domain.model.SearchModel

object DataMapper {

    fun recentSearchEntityToDomain(entity: RecentSearchEntity): SearchModel =
        SearchModel(
            id = entity.id,
            city = entity.city,
            cityCode = entity.cityCode,
            airport = entity.airport
        )

    fun recentSearchDomainToEntity(domain: SearchModel): RecentSearchEntity =
        RecentSearchEntity(
            id = domain.id,
            city = domain.city,
            cityCode = domain.cityCode,
            airport = domain.airport
        )

    fun airportsResponseToSearchDomain(response: ContentItem) : SearchModel =
        SearchModel(
            id = response.id ?: "",
            city = response.city ?: "",
            cityCode = response.cityCode ?: "",
            airport = response.name ?: ""
        )
}
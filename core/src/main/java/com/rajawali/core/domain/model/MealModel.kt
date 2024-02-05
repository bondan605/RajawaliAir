package com.rajawali.core.domain.model

data class MealModel(
    val id: String,
    val price: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String
)

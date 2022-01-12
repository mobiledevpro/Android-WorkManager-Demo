package com.mobiledevpro.watchlist.list.domain.model


data class Stock(
    val symbol: String,
    val companyName: String = "",
    val price: Float = 0F,
    val priceChange: Float = 0F,
    val priceChangePercentage: Float = 0F,
    val lastUpdateTimeMs: Long = 0
)

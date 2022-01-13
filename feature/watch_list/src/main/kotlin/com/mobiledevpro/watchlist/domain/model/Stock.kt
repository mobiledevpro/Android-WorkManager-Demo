package com.mobiledevpro.watchlist.domain.model


data class Stock(
    val symbol: String,
    val companyName: String = "",
    val price: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
    val lastUpdateTimeMs: Long = 0
)

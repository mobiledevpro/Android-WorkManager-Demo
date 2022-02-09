package com.mobiledevpro.watchlist.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.mobiledevpro.resources.R
import com.mobiledevpro.utils.toTimeString
import java.text.DecimalFormat


data class Stock(
    val symbol: String,
    val companyName: String = "",
    val price: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
    val lastUpdateTimeMs: Long = 0,
    val isMarketOpen: Boolean = true
) {

    //Format : +15.34 (+0.54%)
    fun getPriceChangeStr() =
        if (priceChange > 0)
            "+$priceChange (+$priceChangePercentage%)"
        else "$priceChange ($priceChangePercentage%)"

    fun getLastUpdateTimeStr() = lastUpdateTimeMs.toTimeString("MMM d | h:mm a")

    fun getPriceStr() = "$${DecimalFormat("#,###.##").format(price)}"

    @ColorRes
    fun getPriceChangeColorResId(): Int = when {
        priceChange > 0 -> R.color.green
        priceChange < 0 -> R.color.red
        else -> R.color.colorDivider
    }

    @DrawableRes
    fun getMarketOpenIconResId(): Int = when {
        isMarketOpen -> R.drawable.ic_time_green_24dp
        else -> R.drawable.ic_time_yellow_24dp
    }
}

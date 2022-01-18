package com.mobiledevpro.alertlog.core.domain.model

import com.mobiledevpro.utils.toTimeString

/**
 * Model for Alert Log list
 *
 * Created on Jan 17, 2022.
 *
 */
data class StockAlert(
    val symbol: String,
    val message: String,
    val timeMs: Long
) {
    fun getTimeStr() = timeMs.toTimeString("MMM d | h:mm:ss a")

}

package com.mobiledevpro.database.entity

import androidx.room.Entity
import androidx.room.Index


@Entity(
    tableName = "stock_alert",
    indices = [
        Index(value = ["timeMs"])
    ],
    primaryKeys = ["timeMs"]
)
data class StockAlertEntity(
    val timeMs: Long,
    val stockSymbol: String,
    val message: String
)

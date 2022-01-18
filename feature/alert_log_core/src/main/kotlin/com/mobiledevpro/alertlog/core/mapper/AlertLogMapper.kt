/*
 * Copyright 2021 | Dmitri Chernysh | http://mobile-dev.pro
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.mobiledevpro.alertlog.core.mapper

import com.mobiledevpro.alertlog.core.data.model.StockAlertData
import com.mobiledevpro.alertlog.core.domain.model.StockAlert
import com.mobiledevpro.database.entity.StockAlertEntity


fun StockAlert.toData(): StockAlertData =
    StockAlertData(
        symbol, message, timeMs
    )

fun StockAlertData.toDomain(): StockAlert =
    StockAlert(
        symbol, message, timeMs
    )

fun StockAlertEntity.toData(): StockAlertData =
    StockAlertData(
        stockSymbol, message, timeMs
    )

fun StockAlertData.toEntity(): StockAlertEntity =
    StockAlertEntity(
        timeMs, symbol, message
    )

fun List<StockAlertEntity>.toData(): List<StockAlertData> =
    mapTo(ArrayList<StockAlertData>(), StockAlertEntity::toData)

fun List<StockAlertData>.toDomain(): List<StockAlert> =
    mapTo(ArrayList<StockAlert>(), StockAlertData::toDomain)
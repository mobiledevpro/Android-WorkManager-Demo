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
package com.mobiledevpro.alertlog.core.data.repository

import com.mobiledevpro.alertlog.core.data.local.AlertLogLocalSource
import com.mobiledevpro.alertlog.core.data.model.StockAlertData
import com.mobiledevpro.alertlog.core.mapper.toData
import com.mobiledevpro.alertlog.core.mapper.toEntity
import com.mobiledevpro.database.entity.StockAlertEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * It uses to save alerts into local database and get data from database
 *
 * Created on Jan 18, 2022.
 *
 */
class ImplAlertLogRepository(
    private val localSource: AlertLogLocalSource
) : AlertLogRepository {

    override fun getLocal(): Observable<List<StockAlertData>> =
        localSource.get()
            .map(List<StockAlertEntity>::toData)

    override fun insertLocal(alert: StockAlertData): Completable =
        Single.just(alert)
            .map(StockAlertData::toEntity)
            .flatMapCompletable(localSource::insert)
}
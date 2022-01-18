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

import com.mobiledevpro.alertlog.core.data.model.StockAlertData
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * It uses to save alerts into local database and get data from database
 *
 * Created on Jan 18, 2022.
 *
 */
interface AlertLogRepository {

    fun getLocal(): Observable<List<StockAlertData>>

    fun insertLocal(alert: StockAlertData): Completable
}
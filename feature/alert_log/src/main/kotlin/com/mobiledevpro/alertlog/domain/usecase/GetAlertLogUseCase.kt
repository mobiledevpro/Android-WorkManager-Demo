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
package com.mobiledevpro.alertlog.domain.usecase

import com.mobiledevpro.alertlog.core.data.model.StockAlertData
import com.mobiledevpro.alertlog.core.data.repository.AlertLogRepository
import com.mobiledevpro.alertlog.core.domain.model.StockAlert
import com.mobiledevpro.alertlog.core.mapper.toDomain
import com.mobiledevpro.rx.None
import com.mobiledevpro.rx.executor.ExecutionThread
import com.mobiledevpro.rx.executor.PostExecutionThread
import com.mobiledevpro.rx.usecase.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Use case to getting a list of alerts for Alert Log screen
 *
 * Created on Jan 17, 2022.
 *
 */

class GetAlertLogUseCase(
    threadExecutor: ExecutionThread,
    postExecutionThread: PostExecutionThread,
    private val repository: AlertLogRepository
) : ObservableUseCase<List<StockAlert>, None>(
    threadExecutor,
    postExecutionThread
) {

    private val fakeTimMs: Long = 1642109400000

    override fun buildUseCaseObservable(params: None?): Observable<List<StockAlert>> =
        repository.getLocal()
            .map(List<StockAlertData>::toDomain)


    private fun createFakeList(): Single<List<StockAlert>> =
        Single.create { emitter ->
            if (emitter.isDisposed) return@create

            val list = arrayListOf<StockAlert>()

            com.mobiledevpro.alertlog.core.domain.model.StockAlert(
                "GOOG",
                "Crossing price 2,848.00 (demo mode)",
                fakeTimMs
            ).also(list::add)

            com.mobiledevpro.alertlog.core.domain.model.StockAlert(
                "GOOG",
                "SHORT signal on 2,840.00 (demo mode)",
                fakeTimMs
            ).also(list::add)

            com.mobiledevpro.alertlog.core.domain.model.StockAlert(
                "NFLX",
                "LONG signal on 530.00 (demo mode)",
                fakeTimMs
            ).also(list::add)


            emitter.onSuccess(list)
        }

}
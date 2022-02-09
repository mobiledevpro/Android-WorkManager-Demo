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
package com.mobiledevpro.worker.price.alerter.domain.interactor

import com.mobiledevpro.alertlog.core.domain.model.StockAlert
import com.mobiledevpro.alertlog.core.domain.usecase.InsertAlertUseCase
import com.mobiledevpro.rx.None
import com.mobiledevpro.rx.RxResult
import com.mobiledevpro.rx.toViewResult
import io.reactivex.Single
import java.util.*


/**
 * Interactor for [com.mobiledevpro.worker.price.alerter.PriceAlerterWorker]
 *
 * Created on Dec 27, 2021.
 *
 */

class ImplPriceAlerterInteractor(
    private val insertAlertUseCase: InsertAlertUseCase
) : PriceAlerterInteractor {

    override fun createDemoAlert(): Single<RxResult<None>> =
        Single.just(
            StockAlert(
                "DEMO",
                "LONG signal on 1,000.00",
                Date().time
            )
        )
            .flatMapCompletable(insertAlertUseCase::execute)
            .toViewResult()
}
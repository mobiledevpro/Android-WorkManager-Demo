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
package com.mobiledevpro.home.domain.interactor

import com.mobiledevpro.alertlog.core.domain.model.StockAlert
import com.mobiledevpro.alertlog.core.domain.usecase.InsertAlertUseCase
import com.mobiledevpro.rx.None
import com.mobiledevpro.rx.RxResult
import com.mobiledevpro.rx.toViewResult
import io.reactivex.Single
import java.util.*

/**
 * Interactor for Home screen
 *
 * Created on Feb 08, 2022.
 *
 */
class ImplHomeInteractor(
    private val insertAlertUseCase: InsertAlertUseCase
) : HomeInteractor {

    override fun addAlertOnStart(): Single<RxResult<None>> =
        Single.just(
            StockAlert(
                "EVENT",
                "Price Alerter started",
                Date().time
            )
        ).flatMapCompletable(insertAlertUseCase::execute)
            .toViewResult()

    override fun addAlertOnStop(): Single<RxResult<None>> =
        Single.just(
            StockAlert(
                "EVENT",
                "Price Alerter stopped",
                Date().time
            )
        ).flatMapCompletable(insertAlertUseCase::execute)
            .toViewResult()

}
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
package com.mobiledevpro.worker.price.alerter.di

import com.mobiledevpro.alertlog.core.data.local.AlertLogLocalSource
import com.mobiledevpro.alertlog.core.data.local.ImplAlertLogLocalSource
import com.mobiledevpro.alertlog.core.data.repository.AlertLogRepository
import com.mobiledevpro.alertlog.core.data.repository.ImplAlertLogRepository
import com.mobiledevpro.alertlog.core.domain.usecase.InsertAlertUseCase
import com.mobiledevpro.rx.executor.Execution
import com.mobiledevpro.worker.price.alerter.PriceAlerterWorker
import com.mobiledevpro.worker.price.alerter.domain.interactor.ImplPriceAlerterInteractor
import com.mobiledevpro.worker.price.alerter.domain.interactor.PriceAlerterInteractor
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module
 *
 * Created on Dec 27, 2021.
 *
 */

val featurePriceAlerterModule = module {

    factory<PriceAlerterInteractor> {
        ImplPriceAlerterInteractor(
            insertAlertUseCase = get()
        )
    }

    factory {
        InsertAlertUseCase(
            threadExecutor = get(named(Execution.THREAD_IO)),
            postExecutionThread = get(named(Execution.THREAD_MAIN)),
            repository = get()
        )
    }

    factory<AlertLogRepository> {
        ImplAlertLogRepository(
            localSource = get()
        )
    }

    factory<AlertLogLocalSource> {
        ImplAlertLogLocalSource(
            database = get()
        )
    }

    worker {
        PriceAlerterWorker(
            appContext = get(),
            params = get(),
            interactor = get()
        )
    }
}
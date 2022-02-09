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
package com.mobiledevpro.alertlog.di

import com.mobiledevpro.alertlog.domain.interactor.AlertLogInteractor
import com.mobiledevpro.alertlog.domain.interactor.ImplAlertLogInteractor
import com.mobiledevpro.alertlog.domain.usecase.GetAlertLogUseCase
import com.mobiledevpro.alertlog.view.AlertLogFragment
import com.mobiledevpro.alertlog.view.AlertLogViewModel
import com.mobiledevpro.rx.executor.Execution
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Koin module
 *
 * Created on Jan 06, 2022.
 *
 */
val featureAlertLogModule = module {
    scope<AlertLogFragment> {
        viewModel {
            AlertLogViewModel(
                interactor = get()
            )
        }

        scoped<AlertLogInteractor> {
            ImplAlertLogInteractor(
                getAlertLogUseCase = get()
            )
        }

        scoped {
            GetAlertLogUseCase(
                threadExecutor = get(named(Execution.THREAD_IO)),
                postExecutionThread = get(named(Execution.THREAD_MAIN)),
                repository = get()
            )
        }
    }

}
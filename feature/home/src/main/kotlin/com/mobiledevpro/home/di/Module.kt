/*
 * Copyright 2020 | Dmitri Chernysh | http://mobile-dev.pro
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
package com.mobiledevpro.home.di

import com.mobiledevpro.alertlog.core.domain.usecase.InsertAlertUseCase
import com.mobiledevpro.home.domain.interactor.HomeInteractor
import com.mobiledevpro.home.domain.interactor.ImplHomeInteractor
import com.mobiledevpro.home.view.HomeFragment
import com.mobiledevpro.home.view.HomeViewModel
import com.mobiledevpro.rx.executor.Execution
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val featureHomeModule = module {
    scope<HomeFragment> {
        viewModel {
            HomeViewModel(
                workManager = get(),
                interactor = get()
            )
        }

        scoped<HomeInteractor> {
            ImplHomeInteractor(
                insertAlertUseCase = get()
            )
        }

        scoped {
            InsertAlertUseCase(
                threadExecutor = get(named(Execution.THREAD_IO)),
                postExecutionThread = get(named(Execution.THREAD_MAIN)),
                repository = get()
            )
        }
    }
}

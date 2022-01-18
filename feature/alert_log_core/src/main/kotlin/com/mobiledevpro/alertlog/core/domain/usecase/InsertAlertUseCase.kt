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
package com.mobiledevpro.alertlog.core.domain.usecase

import com.mobiledevpro.alertlog.core.domain.model.StockAlert
import com.mobiledevpro.rx.executor.ExecutionThread
import com.mobiledevpro.rx.executor.PostExecutionThread
import com.mobiledevpro.rx.usecase.CompletableUseCase
import io.reactivex.Completable

/**
 * Use case to add a new alert ito a local Alert Log
 *
 * Created on Jan 18, 2022.
 *
 */

class InsertAlertUseCase(
    threadExecutor: ExecutionThread,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<StockAlert>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: StockAlert?): Completable =
        Completable.complete()

}
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
package com.mobiledevpro.watchlist.list.domain.usecase

import com.mobiledevpro.rx.executor.ExecutionThread
import com.mobiledevpro.rx.executor.PostExecutionThread
import com.mobiledevpro.rx.usecase.ObservableUseCase
import io.reactivex.Observable

/**
 * Use case to getting a list of saved stocks for Watchlist
 *
 * Created on Jan 12, 2022.
 *
 */

class GetWatchListUseCase(
    threadExecutor: ExecutionThread,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<String>, String>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<List<String>> =
        Observable.just(emptyList())

}
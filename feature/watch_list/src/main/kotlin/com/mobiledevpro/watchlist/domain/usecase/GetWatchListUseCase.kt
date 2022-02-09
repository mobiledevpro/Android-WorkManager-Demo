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
package com.mobiledevpro.watchlist.domain.usecase

import com.mobiledevpro.rx.executor.ExecutionThread
import com.mobiledevpro.rx.executor.PostExecutionThread
import com.mobiledevpro.rx.usecase.ObservableUseCase
import com.mobiledevpro.watchlist.domain.model.Stock
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/**
 * Use case to getting a list of saved stocks for Watchlist
 *
 * Created on Jan 12, 2022.
 *
 */

class GetWatchListUseCase(
    threadExecutor: ExecutionThread,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Stock>, String>(threadExecutor, postExecutionThread) {

    private val fakeTimMs: Long = Calendar.getInstance(Locale.getDefault()).let { calendar ->
        calendar.add(Calendar.MINUTE, -15)
        calendar.timeInMillis
    }

    override fun buildUseCaseObservable(params: String?): Observable<List<Stock>> =
        createFakeList()
            .toObservable()


    private fun createFakeList(): Single<List<Stock>> =
        Single.create { emitter ->
            if (emitter.isDisposed) return@create

            val list = arrayListOf<Stock>()

            Stock(
                "GOOG",
                "Alphabet Inc Class C",
                2848.30,
                15.34,
                0.54,
                fakeTimMs
            ).also(list::add)

            Stock(
                "FB",
                "Meta Platforms Inc",
                330.93,
                -2.30,
                -0.75,
                fakeTimMs
            ).also(list::add)

            Stock(
                "NFLX",
                "Netflix Inc",
                532.29,
                -5.00,
                -0.93,
                fakeTimMs
            ).also(list::add)

            emitter.onSuccess(list)
        }

}
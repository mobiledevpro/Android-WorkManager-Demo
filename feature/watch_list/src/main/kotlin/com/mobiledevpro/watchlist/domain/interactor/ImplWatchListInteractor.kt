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
package com.mobiledevpro.watchlist.domain.interactor

import com.mobiledevpro.rx.RxResult
import com.mobiledevpro.rx.toViewResult
import com.mobiledevpro.watchlist.domain.model.Stock
import com.mobiledevpro.watchlist.domain.usecase.GetWatchListUseCase
import io.reactivex.Observable


class ImplWatchListInteractor(
    private val getWatchListUseCase: GetWatchListUseCase
) : WatchListInteractor {

    override fun get(): Observable<RxResult<List<Stock>>> =
        getWatchListUseCase.execute()
            .toViewResult()
}
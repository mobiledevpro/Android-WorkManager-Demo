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
package com.mobiledevpro.watchlist.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobiledevpro.common.ui.base.BaseViewModel
import com.mobiledevpro.recycler.RecyclerItem
import com.mobiledevpro.recycler.RecyclerViewArgs
import com.mobiledevpro.recycler.RecyclerViewHandler
import com.mobiledevpro.recycler.mapper.toRecyclerView
import com.mobiledevpro.rx.RxResult
import com.mobiledevpro.watchlist.domain.interactor.WatchListInteractor
import com.mobiledevpro.watchlist.list.BR
import com.mobiledevpro.watchlist.list.R
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * View model for Watchlist screen for HomePagerAdapter
 *
 * Created on Jan 06, 2022.
 *
 */
class WatchListViewModel(
    val interactor: WatchListInteractor
) : BaseViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _listStock = MutableLiveData<List<RecyclerItem>?>()
    val listStock: LiveData<List<RecyclerItem>?> = _listStock

    private val recyclerViewArgs = RecyclerViewArgs(
        R.layout.item_stock,
        BR.stock,
        BR.handler
    )

    val listEventHandler = object : RecyclerViewHandler {
        override fun onClickItem(item: Any) {

            //TODO: handle clicking on items if needed
        }
    }

    init {
        observeWatchList()
    }

    private fun observeWatchList() {
        interactor.get()
            .subscribeBy {
                when (it) {
                    is RxResult.Success -> it.data
                        .toRecyclerView(recyclerViewArgs)
                        .also(_listStock::postValue)
                    is RxResult.Failure -> it.throwable.let { error ->
                        val msg = error.localizedMessage
                        _errorMessage.value = msg
                    }
                }
            }
            .addTo(subscriptions)
    }
}
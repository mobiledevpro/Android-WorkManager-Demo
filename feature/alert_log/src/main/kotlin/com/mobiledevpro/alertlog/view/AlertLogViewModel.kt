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
package com.mobiledevpro.alertlog.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobiledevpro.alertlog.BR
import com.mobiledevpro.alertlog.R
import com.mobiledevpro.alertlog.domain.interactor.AlertLogInteractor
import com.mobiledevpro.common.ui.base.BaseViewModel
import com.mobiledevpro.recycler.RecyclerItem
import com.mobiledevpro.recycler.RecyclerViewArgs
import com.mobiledevpro.recycler.RecyclerViewHandler
import com.mobiledevpro.recycler.mapper.toRecyclerView
import com.mobiledevpro.rx.RxResult
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * View model for Alert log screen for HomePagerAdapter
 *
 * Created on Jan 06, 2022.
 *
 */

class AlertLogViewModel(
    private val interactor: AlertLogInteractor
) : BaseViewModel() {

    private val _listAlert = MutableLiveData<List<RecyclerItem>?>()
    val listAlert: LiveData<List<RecyclerItem>?> = _listAlert

    private val recyclerViewArgs = RecyclerViewArgs(
        R.layout.item_stock_alert,
        BR.alert,
        BR.handler
    )

    val listEventHandler = object : RecyclerViewHandler {
        override fun onClickItem(item: Any) {

            //TODO: handle clicking on items if needed
        }
    }

    init {
        observeAlertList()
    }

    private fun observeAlertList() {
        interactor.get()
            .subscribeBy {
                when (it) {
                    is RxResult.Success -> it.data
                        .toRecyclerView(recyclerViewArgs)
                        .also(_listAlert::postValue)
                    is RxResult.Failure -> it.throwable.let { error ->
                        val msg = error.localizedMessage
                        Log.e(this::class.java.name, "observeAlertList: $msg", error)
                    }
                }
            }
            .addTo(subscriptions)
    }
}
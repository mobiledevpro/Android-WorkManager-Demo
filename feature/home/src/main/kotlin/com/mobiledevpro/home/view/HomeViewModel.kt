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
package com.mobiledevpro.home.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mobiledevpro.common.ui.base.BaseViewModel
import com.mobiledevpro.common.ui.livedata.SingleLiveData
import com.mobiledevpro.home.domain.interactor.HomeInteractor
import com.mobiledevpro.navigation.NavigateTo
import com.mobiledevpro.navigation.Navigation
import com.mobiledevpro.utils.WORKER_PRICE_ALERT_TAG
import com.mobiledevpro.worker.price.alerter.PriceAlerterWorker
import com.mobiledevpro.workmanager.cancelByTag
import com.mobiledevpro.workmanager.runUniqueWork
import com.mobiledevpro.workmanager.setDefaultConstraints
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

/**
 * View model for Home screen
 *
 * Created on Dec 03, 2021.
 *
 */

class HomeViewModel(
    private val workManager: WorkManager,
    private val interactor: HomeInteractor
) : BaseViewModel() {

    val eventNavigateTo = SingleLiveData<Navigation>()

    fun onClickStartSchedule() {

        startPeriodicWorker()

        //Save event into local database
        interactor.addAlertOnStart()
            .subscribeBy()
            .addTo(subscriptions)

        //Navigate to Alert Log
        Navigation(NavigateTo.ALERT_LOG)
            .let(eventNavigateTo::postValue)
    }

    fun onClickStopSchedule() {

        stopPeriodicWorker()

        //Save event into local database
        interactor.addAlertOnStop()
            .subscribeBy()
            .addTo(subscriptions)
    }

    fun isPeriodicWorkRunning(): LiveData<Boolean> {
        val isRunning = MediatorLiveData<Boolean>()

        val check: (List<WorkInfo>) -> Boolean = { workInfoList ->
            val info: WorkInfo? = if (workInfoList.isNotEmpty()) workInfoList.last() else null
            Log.d("WorkerTest", "workInfo: $info ")

            info?.state?.isFinished == false
        }

        isRunning.value = false

        isRunning.addSource(workManager.getWorkInfosByTagLiveData(WORKER_PRICE_ALERT_TAG)) { workInfoList ->
            isRunning.value = check(workInfoList)
        }

        return isRunning
    }


    private fun startPeriodicWorker() {
        PeriodicWorkRequestBuilder<PriceAlerterWorker>(
            15, TimeUnit.MINUTES
        )
            .setDefaultConstraints()
            .addTag(WORKER_PRICE_ALERT_TAG)
            .build()
            .let { request ->
                workManager.runUniqueWork(
                    request,
                    "${WORKER_PRICE_ALERT_TAG}_periodic"
                )
            }
    }

    private fun stopPeriodicWorker() {
        workManager.cancelByTag(WORKER_PRICE_ALERT_TAG)
    }

}
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

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mobiledevpro.common.ui.base.BaseViewModel
import com.mobiledevpro.worker.price.alerter.PriceAlerterWorker
import com.mobiledevpro.workmanager.runUniqueWork
import com.mobiledevpro.workmanager.setDefaultBackOffCriteria
import com.mobiledevpro.workmanager.setDefaultConstraints
import java.util.concurrent.TimeUnit

/**
 * View model for Home screen
 *
 * Created on Dec 03, 2021.
 *
 */

class HomeViewModel(
    private val workManager: WorkManager
) : BaseViewModel() {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartView() {
        //do something on start view if it's needed
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopView() {
        //do something on stop view if it's needed
    }

    fun onClickSchedule() {
        //startOnetimeWorker()
        startPeriodicWorker()
    }

    /*
    fun getUploadFilesWorkerInfo(): LiveData<WorkInfo> =
        workMangerUtil.getLastWorkerInfo()

     */

    private fun startOnetimeWorker() {
        OneTimeWorkRequestBuilder<PriceAlerterWorker>()
            .setDefaultConstraints()
            .setDefaultBackOffCriteria()
            .build()
            .let { request ->

                workManager.runUniqueWork(
                    request,
                    "${PriceAlerterWorker::class.java.name}_onetime"
                )
            }

    }

    private fun startPeriodicWorker() {
        PeriodicWorkRequestBuilder<PriceAlerterWorker>(15, TimeUnit.MINUTES)
            .setDefaultConstraints()
            .build()
            .let { request ->
                workManager.runUniqueWork(
                    request,
                    "${PriceAlerterWorker::class.java.name}_periodic"
                )
            }
    }

}
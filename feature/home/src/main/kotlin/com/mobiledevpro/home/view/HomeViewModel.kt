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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mobiledevpro.common.ui.base.BaseViewModel
import com.mobiledevpro.worker.price.alerter.PriceAlerterWorker
import com.mobiledevpro.workmanager.cancelByTag
import com.mobiledevpro.workmanager.runUniqueWork
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

    fun onClickStartSchedule() {
        //startOnetimeWorker()
        startPeriodicWorker()
    }

    fun onClickStopSchedule() {
        workManager.cancelByTag(PriceAlerterWorker.TAG)
    }

    fun isSchedulerRunning(): LiveData<Boolean> {
        val isRunning = MediatorLiveData<Boolean>()

        val check: (List<WorkInfo>) -> Boolean = { workInfoList ->
            val info: WorkInfo? = if (workInfoList.isNotEmpty()) workInfoList.last() else null
            Log.d("WorkerTest", "workInfo: $info ")

            info?.state?.isFinished == false
        }

        isRunning.value = false

        isRunning.addSource(workManager.getWorkInfosByTagLiveData(PriceAlerterWorker.TAG)) { workInfoList ->
            isRunning.value = check(workInfoList)
        }

        return isRunning
    }


    private fun startPeriodicWorker() {
        PeriodicWorkRequestBuilder<PriceAlerterWorker>(15, TimeUnit.MINUTES)
            .setDefaultConstraints()
            .addTag(PriceAlerterWorker.TAG)
            .build()
            .let { request ->
                workManager.runUniqueWork(
                    request,
                    "${PriceAlerterWorker::class.java.name}_periodic"
                )
            }
    }

    /*
    private fun startOnetimeWorker() {
        OneTimeWorkRequestBuilder<PriceAlerterWorker>()
            .setDefaultConstraints()
            .setDefaultBackOffCriteria()
            .addTag(PriceAlerterWorker.TAG)
            .build()
            .let { request ->

                workManager.runUniqueWork(
                    request,
                    "${PriceAlerterWorker::class.java.name}_onetime"
                )
            }
    }

 */

}
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
package com.mobiledevpro.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit


class WorkManagerUtil(
    private val appContext: Context
) {

    private val customConfig = Configuration.Builder()
        .setMinimumLoggingLevel(Log.DEBUG)
        .build()

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    private val onetimeUploadFilesWorkerRequestBuilder =
        OneTimeWorkRequestBuilder<UploadFilesWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )

    private val periodicUploadFilesWorkerRequestBuilder =
        PeriodicWorkRequestBuilder<UploadFilesWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)

    private val _lastWorkRequestId = MutableLiveData<UUID>()


    init {
        Log.d("WorkerTest", "WorkManagerUtil initialized")
        WorkManager.initialize(appContext, customConfig)
    }

    fun getLastWorkerInfo(): LiveData<WorkInfo> {
        val result = MediatorLiveData<WorkInfo>()

        result.addSource(_lastWorkRequestId) {
            WorkManager.getInstance(appContext)
                .getWorkInfoByIdLiveData(it)
        }

        return result
    }


    fun submitOnetimeWorkerRequest() {

        Log.d("WorkerTest", "WorkManagerUtil submitOnetimeWorkerRequest")
        val request = onetimeUploadFilesWorkerRequestBuilder.build()
        _lastWorkRequestId.value = request.id

        //send request
        WorkManager
            .getInstance(appContext)
            .enqueue(request)
    }

    fun submitPeriodicWorkerRequest() {
        Log.d("WorkerTest", "WorkManagerUtil submitPeriodicWorkerRequest")
        val request = periodicUploadFilesWorkerRequestBuilder.build()
        _lastWorkRequestId.value = request.id

        WorkManager
            .getInstance(appContext)
            .enqueueUniquePeriodicWork(
                "PeriodicWork",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}
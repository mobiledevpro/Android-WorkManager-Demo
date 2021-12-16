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

import android.util.Log
import androidx.work.*


class WorkManagerUtil(
    private val workManager: WorkManager
) {


    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    /*
    fun getLastWorkerInfo(): LiveData<WorkInfo> {
        val result = MediatorLiveData<WorkInfo>()

        result.addSource(_lastWorkRequestId) {
            WorkManager.getInstance(appContext)
                .getWorkInfoByIdLiveData(it)
        }

        return result
    }

     */


    fun enqueue(
        request: OneTimeWorkRequest,
        uniqueRequestName: String,
        policy: ExistingWorkPolicy = ExistingWorkPolicy.KEEP
    ) {

        Log.d("WorkerTest", "WorkManagerUtil enqueue $uniqueRequestName")

        //send request
        workManager
            .enqueueUniqueWork(uniqueRequestName, policy, request)
    }

    fun enqueue(
        request: PeriodicWorkRequest,
        uniqueRequestName: String,
        policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
    ) {

        Log.d("WorkerTest", "WorkManagerUtil enqueue $uniqueRequestName")

        workManager
            .enqueueUniquePeriodicWork(
                uniqueRequestName,
                policy,
                request
            )
    }
}
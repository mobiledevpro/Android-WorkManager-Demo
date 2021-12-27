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
package com.mobiledevpro.workmanager

import android.util.Log
import androidx.work.*


fun WorkManager.enqueue(
    request: OneTimeWorkRequest,
    uniqueRequestName: String,
    policy: ExistingWorkPolicy = ExistingWorkPolicy.KEEP
) {

    Log.d("WorkerTest", "WorkManagerExt enqueue $uniqueRequestName")
    enqueueUniqueWork(uniqueRequestName, policy, request)
}

fun WorkManager.enqueueSequence(
    requestList: List<OneTimeWorkRequest>,
    uniqueRequestName: String,
    policy: ExistingWorkPolicy = ExistingWorkPolicy.KEEP
) {

    if (requestList.isEmpty()) return

    Log.d("WorkerTest", "WorkManagerExt enqueue $uniqueRequestName")

    if (requestList.size == 1)
        enqueueUniqueWork(uniqueRequestName, policy, requestList[0])
    else
        apply {
            var workContinuation: WorkContinuation? = null

            for (i in requestList.indices) {
                workContinuation = if (i == 0)
                    this.beginUniqueWork(uniqueRequestName, policy, requestList[i])
                else
                    workContinuation?.then(requestList[i])
            }

            workContinuation?.enqueue()
        }
}

fun WorkManager.enqueue(
    request: PeriodicWorkRequest,
    uniqueRequestName: String,
    policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
) {

    Log.d("WorkerTest", "WorkManagerExt enqueue $uniqueRequestName")

    enqueueUniquePeriodicWork(
        uniqueRequestName,
        policy,
        request
    )
}

fun WorkManager.cancelByTag(tag: String) =
    cancelAllWorkByTag(tag)

fun WorkManager.isWorkRunning(tag: String): Boolean {
    val list: List<WorkInfo> = getWorkInfosByTag(tag).get()

    var isRunning = false

    for (workInfo in list) {
        Log.d("WorkerTest", "isWorkRunning:  ${workInfo.state}")
        isRunning = workInfo.state == WorkInfo.State.RUNNING
        if (isRunning) break
    }

    Log.d("WorkerTest", "isWorkRunning: $isRunning")

    return isRunning
}

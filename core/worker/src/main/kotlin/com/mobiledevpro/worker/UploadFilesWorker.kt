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
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

/**
 * Worker to upload something to server
 *
 * Created on Dec 06, 2021.
 *
 */
class UploadFilesWorker(appContext: Context, params: WorkerParameters) :
    RxWorker(appContext, params) {

    override fun createWork(): Single<Result> =
        Single.create { emitter ->
            if (emitter.isDisposed) return@create

            Log.d(TAG, "createWork: start")

            //todo:

            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "createWork: EXCEPTION: ${e.localizedMessage}", e)
            }

            Log.d(TAG, "createWork: end")

            emitter.onSuccess(
                Result.success()
            )
        }


    companion object {
        const val TAG = "WorkerTest"
    }

}
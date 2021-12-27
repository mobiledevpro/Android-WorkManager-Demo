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
package com.mobiledevpro.worker.price.alerter

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.mobiledevpro.rx.RxResult
import com.mobiledevpro.rx.toViewResult
import com.mobiledevpro.worker.price.alerter.domain.interactor.PriceAlerterInteractor
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Worker to check stocks price notify about price changes
 *
 * NOTE:
 *
 * #1 Get stock price form API
 * #2 Save data into local database
 * #3 Compare the current and the last price and notify the user about changes.
 *
 * Created on Dec 27, 2021.
 *
 */
class PriceAlerterWorker(
    appContext: Context,
    params: WorkerParameters,
    private val interactor: PriceAlerterInteractor
) : RxWorker(appContext, params) {

    override fun createWork(): Single<Result> =
        test()
            .toViewResult()
            .map { result ->
                when (result) {
                    is RxResult.Success -> {
                        Log.d(TAG, "createWork: Success")
                        Result.success()
                    }
                    is RxResult.Failure -> {
                        Log.d(TAG, "createWork: Failed. Retry.")
                        Result.retry()
                    }
                }
            }


    private fun test(): Completable =
        Completable.create { emitter ->
            if (emitter.isDisposed) return@create

            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "createWork: EXCEPTION: ${e.localizedMessage}", e)
            }

            emitter.onComplete()
        }

    companion object {
        const val TAG = "WorkerTest"
    }
}
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
package com.mobiledevpro.alertlog.core.di

import androidx.fragment.app.Fragment
import com.mobiledevpro.alertlog.core.data.local.AlertLogLocalSource
import com.mobiledevpro.alertlog.core.data.local.ImplAlertLogLocalSource
import com.mobiledevpro.alertlog.core.data.repository.AlertLogRepository
import com.mobiledevpro.alertlog.core.data.repository.ImplAlertLogRepository
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAlertLogCoreModule = module {
    scope(named(SCOPE_NAME_ALERT_LOG_CORE)) {
        scoped<AlertLogRepository> {
            ImplAlertLogRepository(
                localSource = get()
            )
        }

        scoped<AlertLogLocalSource> {
            ImplAlertLogLocalSource(
                database = get()
            )
        }
    }
}

fun Fragment.alertLogCoreScope() = lazy(LazyThreadSafetyMode.NONE) {
   getKoin().getOrCreateScope(SCOPE_ID_ALERT_LOG_CORE, named(SCOPE_NAME_ALERT_LOG_CORE))
}

private const val SCOPE_NAME_ALERT_LOG_CORE = "alert_log_core"
private const val SCOPE_ID_ALERT_LOG_CORE = "alert_log_core"
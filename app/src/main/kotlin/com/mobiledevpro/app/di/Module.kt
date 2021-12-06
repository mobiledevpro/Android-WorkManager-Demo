package com.mobiledevpro.app.di

import com.mobiledevpro.app.helper.ImplResourcesProvider
import com.mobiledevpro.app.helper.ResourcesProvider
import com.mobiledevpro.database.di.coreDatabaseModule
import com.mobiledevpro.rx.di.coreRxModule
import com.mobiledevpro.worker.WorkManagerUtil
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


/**
 * App Koin module
 *
 */
fun getModules() = listOf(
    presentationCommonModule,
    coreRxModule,
    coreDatabaseModule,
    coreWorkerModule
)

val presentationCommonModule = module {
    single<ResourcesProvider> {
        ImplResourcesProvider(androidApplication().resources)
    }
}

val coreWorkerModule = module {
    single {
        WorkManagerUtil(androidApplication().applicationContext)
    }
}


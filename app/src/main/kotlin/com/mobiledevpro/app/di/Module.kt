package com.mobiledevpro.app.di

import com.mobiledevpro.database.di.coreDatabaseModule
import com.mobiledevpro.resources.ImplResourcesProvider
import com.mobiledevpro.resources.ResourcesProvider
import com.mobiledevpro.rx.di.coreRxModule
import com.mobiledevpro.workmanager.di.coreWorkManagerModule
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
    coreWorkManagerModule
)

val presentationCommonModule = module {
    single<ResourcesProvider> {
        ImplResourcesProvider(androidApplication().resources)
    }
}


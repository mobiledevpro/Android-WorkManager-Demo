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
package com.mobiledevpro.alertlog.view

import com.mobiledevpro.alertlog.R
import com.mobiledevpro.alertlog.core.di.alertLogCoreScope
import com.mobiledevpro.alertlog.core.di.featureAlertLogCoreModule
import com.mobiledevpro.alertlog.databinding.FragmentAlertlogBinding
import com.mobiledevpro.alertlog.di.featureAlertLogModule
import com.mobiledevpro.common.ui.base.BaseFragment
import com.mobiledevpro.common.ui.base.FragmentSettings
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope
import com.mobiledevpro.resources.R as RApp

/**
 * Alert log screen for HomePagerAdapter
 *
 * Created on Jan 06, 2022.
 *
 */
class AlertLogFragment : BaseFragment<FragmentAlertlogBinding>(
    layoutId = R.layout.fragment_alertlog,
    FragmentSettings(
        appBarTitle = RApp.string.app_name,
        homeIconId = RApp.drawable.ic_back_arrow_light_24dp
    )
), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()
    private val alertLogCoreScope: Scope by alertLogCoreScope()

    private val viewModel: AlertLogViewModel by lazy(LazyThreadSafetyMode.NONE) { scope.get() }

    init {
        loadKoinModules(
            listOf(
                featureAlertLogModule,
                featureAlertLogCoreModule
            )
        )

        scope.linkTo(alertLogCoreScope)
    }

    override fun onInitDataBinding() {
        viewBinding.model = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun observeLifecycleEvents() {

    }


}
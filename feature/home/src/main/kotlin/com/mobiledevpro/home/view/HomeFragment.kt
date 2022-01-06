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

import androidx.viewpager2.widget.ViewPager2
import com.mobiledevpro.common.ui.base.BaseFragment
import com.mobiledevpro.common.ui.base.FragmentSettings
import com.mobiledevpro.common.ui.extension.observe
import com.mobiledevpro.home.R
import com.mobiledevpro.home.databinding.FragmentHomeBinding
import com.mobiledevpro.home.di.featureHomeModule
import com.mobiledevpro.home.view.adapter.HomePagerAdapter
import com.mobiledevpro.worker.price.alerter.di.featurePriceAlerterModule
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope
import com.mobiledevpro.resources.R as RApp

/**
 * Home screen
 *
 * Created on Dec 03, 2021.
 *
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    layoutId = R.layout.fragment_home,
    FragmentSettings(
        appBarTitle = RApp.string.app_name,
        homeIconId = RApp.drawable.ic_back_arrow_light_24dp,
        statusBarColor = RApp.color.colorPrimary,
        appBarColor = RApp.color.colorPrimary,
        appWindowBackground = RApp.color.colorWindowBackground
    )
), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: HomeViewModel by inject()

    init {
        loadKoinModules(
            arrayListOf(
                featureHomeModule,
                featurePriceAlerterModule
            )
        )
    }

    override fun onInitDataBinding() {
        viewBinding.model = viewModel
        lifecycle.addObserver(viewModel)

        viewBinding.viewPager.init()
    }

    private fun ViewPager2.init() {
        HomePagerAdapter(this@HomeFragment)
            .let(this::setAdapter)
/*
        TabLayoutMediator(
            viewBinding.tabsInventory,
            viewBinding.viewpagerInventory
        ) { tab, position ->
            tab.text = inventoryPagerAdapter.getPageTitle(position)
        }.attach()

 */
    }

    override fun observeLifecycleEvents() {

        observe(viewModel.isSchedulerRunning()) { isRunning ->

            val text = if (isRunning)
                RApp.string.button_stop_schedule
            else
                RApp.string.button_start_schedule

            viewBinding.btnStartStop.apply {
                this.setText(text)
                this.setOnClickListener {
                    if (isRunning)
                        viewModel.onClickStopSchedule()
                    else
                        viewModel.onClickStartSchedule()
                }
            }


        }


    }


}
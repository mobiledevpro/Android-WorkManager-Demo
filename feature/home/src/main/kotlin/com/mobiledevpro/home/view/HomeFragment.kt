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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobiledevpro.alertlog.core.di.alertLogCoreScope
import com.mobiledevpro.alertlog.core.di.featureAlertLogCoreModule
import com.mobiledevpro.common.ui.base.BaseFragment
import com.mobiledevpro.common.ui.base.FragmentSettings
import com.mobiledevpro.common.ui.extension.observe
import com.mobiledevpro.home.R
import com.mobiledevpro.home.databinding.FragmentHomeBinding
import com.mobiledevpro.home.di.featureHomeModule
import com.mobiledevpro.home.view.adapter.HomePagerAdapter
import com.mobiledevpro.navigation.NavigateTo
import com.mobiledevpro.utils.BOTTOM_MENU_ALERT_LOG_POSITION
import com.mobiledevpro.utils.BOTTOM_MENU_WATCHLIST_POSITION
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
    private val alertLogCoreScope: Scope by alertLogCoreScope()

    private val viewModel: HomeViewModel by inject(mode = LazyThreadSafetyMode.NONE)


    init {
        loadKoinModules(
            arrayListOf(
                featureHomeModule,
                featurePriceAlerterModule,
                featureAlertLogCoreModule
            )
        )

        scope.linkTo(alertLogCoreScope)
    }

    override fun onInitDataBinding() {
        viewBinding.model = viewModel
        lifecycle.addObserver(viewModel)

        viewBinding.viewPager.init()
        viewBinding.bottomNavigation.init()
    }


    private fun ViewPager2.init() {
        offscreenPageLimit = 2

        HomePagerAdapter(this@HomeFragment)
            .let(this::setAdapter)

        // Attach ViewPager to Bottom Navigation
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewBinding.bottomNavigation.menu.let { menu ->
                    when (position) {
                        0 -> menu.getItem(BOTTOM_MENU_WATCHLIST_POSITION).isChecked = true
                        1 -> menu.getItem(BOTTOM_MENU_ALERT_LOG_POSITION).isChecked = true
                    }
                }
            }
        })
    }

    private fun BottomNavigationView.init() {

        //Attach Bottom Navigation to ViewPager
        setOnItemSelectedListener { menuItem ->

            viewBinding.viewPager.let { pager ->
                pager.currentItem = when (menuItem.itemId) {
                    R.id.item_stock_list -> 0
                    R.id.item_alert_log -> 1
                    else -> 0
                }
            }

            true
        }

    }

    override fun observeLifecycleEvents() {

        observe(viewModel.isPeriodicWorkRunning()) { isRunning ->

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

        observe(viewModel.eventNavigateTo) { nav ->
            when (nav.to) {
                NavigateTo.ALERT_LOG -> {
                    viewBinding.viewPager.setCurrentItem(1, false)
                }
                else -> {}
            }

        }


    }


}
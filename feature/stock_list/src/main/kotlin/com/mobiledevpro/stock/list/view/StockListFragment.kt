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
package com.mobiledevpro.stock.list.view

import com.mobiledevpro.common.ui.base.BaseFragment
import com.mobiledevpro.common.ui.base.FragmentSettings
import com.mobiledevpro.stock.list.R
import com.mobiledevpro.stock.list.databinding.FragmentStockListBinding
import com.mobiledevpro.stock.list.di.featureStockListModule
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope

/**
 * Stock list screen for HomePagerAdapter
 *
 * Created on Jan 06, 2022.
 *
 */
class StockListFragment : BaseFragment<FragmentStockListBinding>(
    layoutId = R.layout.fragment_stock_list,
    FragmentSettings(
        appBarTitle = 0,
        homeIconId = 0
    )
), AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val viewModel: StockListViewModel by lazy(LazyThreadSafetyMode.NONE) { scope.get() }

    init {
        loadKoinModules(featureStockListModule)
    }

    override fun onInitDataBinding() {
        viewBinding.model = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun observeLifecycleEvents() {

    }


}
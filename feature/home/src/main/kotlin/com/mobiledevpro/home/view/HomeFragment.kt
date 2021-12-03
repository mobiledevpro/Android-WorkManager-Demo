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

import com.mobiledevpro.common.ui.base.BaseFragment
import com.mobiledevpro.common.ui.base.FragmentSettings
import com.mobiledevpro.home.R
import com.mobiledevpro.home.databinding.FragmentHomeBinding
import com.mobiledevpro.home.di.inject
import com.mobiledevpro.app.R as RApp

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
        appWindowBackground = RApp.color.colorWindowGreyBackground
    )
) {

    private val viewModel: HomeViewModel by inject()


    override fun onInitDataBinding() {
        viewBinding.model = viewModel
        lifecycle.addObserver(viewModel)
    }

    override fun observeLifecycleEvents() {
        /*  observe(viewModel.appbarTitle, observer = { title ->
              (requireActivity() is BaseActivityInterface).apply {
                  (requireActivity() as BaseActivityInterface).setAppBarTitle(title)
              }
          })

          observe(viewModel.eventNavigateTo, observer = { navigation ->
              try {
                  openScreen(navigation)
              } catch (e: RuntimeException) {
                  val err = e.localizedMessage
                  if (!err.isNullOrEmpty())
                      showErrorDialog(err)
              }
          })

         */
    }


}
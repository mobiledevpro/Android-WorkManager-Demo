/*
 * Copyright 2020 | Dmitri Chernysh | http://mobile-dev.pro
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
package com.mobiledevpro.recycler.ext

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevpro.recycler.RecyclerItem
import com.mobiledevpro.recycler.RecyclerViewAdapter
import com.mobiledevpro.recycler.RecyclerViewHandler

/**
 * Extension for RecyclerView
 *
 * Created on Dec 15, 2020.
 *
 */

fun RecyclerView.set(
    items: List<RecyclerItem>?,
    handler: RecyclerViewHandler?,
    divider: Drawable?
) {
    this.itemAnimator = DefaultItemAnimator()

    //This approach adds some vertical space we don't need
    // val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
    //  divider.setDrawable(BaseResourcesHelper.getDrawableCompatible(context, R.drawable.item_divider))
    // this.addItemDecoration(divider)

    //Add divider
    if (this.itemDecorationCount == 0)
        divider?.let { dividerDrawable ->

            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                .apply {
                    setDrawable(dividerDrawable)
                }
                .also(this::addItemDecoration)
        }

    var adapter = (this.adapter as? RecyclerViewAdapter)
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        this.adapter = adapter
    }

    adapter.setEventHandler(handler)
    adapter.updateData(items.orEmpty())
}

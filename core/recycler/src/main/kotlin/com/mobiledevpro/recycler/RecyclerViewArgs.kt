package com.mobiledevpro.recycler

import androidx.annotation.LayoutRes

/**
 * Arguments for Recycler View item with the Data Binding support
 *
 * Created on Jan 13, 2022.
 *
 */
data class RecyclerViewArgs(
    @LayoutRes
    val layoutResId: Int,
    val dataVariableId: Int,
    val handlerVariableId: Int?
)

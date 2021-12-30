package com.mobiledevpro.app.ui.mainscreen.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.mobiledevpro.app.R
import com.mobiledevpro.common.ui.base.ActivitySettings
import com.mobiledevpro.common.ui.base.BaseActivity
import com.mobiledevpro.common.ui.extension.getColorCompatible
import com.mobiledevpro.resources.R as RApp

class MainActivity : BaseActivity(
    layoutId = R.layout.activity_main,
    ActivitySettings(
        isAdjustFontScaleToNormal = true,
        windowFlags = listOf(
            //set navigation bar translucent
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    )
) {

    override fun initToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
        toolbar?.let {
            setSupportActionBar(it)
        }

        window.apply {
            getColorCompatible(RApp.color.colorSystemBarTransparent)
                .let { color ->
                    navigationBarColor = color
                }
        }
    }

    override fun initViews(layoutView: View) {
        //do something: as example, init bottom navigation.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(android.R.id.content)
            .let(this::applyWindowInsets)
    }


    private fun applyWindowInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->

            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = 0
                rightMargin = insets.right
                topMargin = insets.top
            }

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            windowInsets //WindowInsetsCompat.CONSUMED
        }
    }
}

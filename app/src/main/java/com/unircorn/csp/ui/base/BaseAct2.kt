package com.unircorn.csp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.jzvd.Jzvd
import com.unircorn.csp.R


abstract class BaseAct2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_base)

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) return

        // Create a new Fragment to be placed in the activity layout
        val fragment = createFragment()

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        if (intent.extras != null && fragment.arguments == null) {
            fragment.arguments = intent.extras
        }

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment).commit()
    }

    abstract fun createFragment(): Fragment

    //

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}
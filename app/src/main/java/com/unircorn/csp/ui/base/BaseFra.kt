package com.unircorn.csp.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.unircorn.csp.app.MyComponent

abstract class BaseFra(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), UI {

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initBindings()
        initEvents()
    }

    override fun initViews() {
    }

    override fun initBindings() {
    }

    override fun initEvents() {
    }

    val api by lazy { MyComponent().api }

}


package com.unircorn.csp.ui.act

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.MyFra

class MyAct : BaseAct() {
    override fun createFragment() = MyFra()
}
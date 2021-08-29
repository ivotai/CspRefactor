package com.unircorn.csp.ui.act

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.LoginFra
import com.unircorn.csp.ui.fra.TestFra

class TestAct : BaseAct() {

    override fun createFragment() = TestFra()

}
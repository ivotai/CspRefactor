package com.unircorn.csp.ui.act

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.LoginFra

class LoginAct : BaseAct() {

    override fun createFragment() = LoginFra()

}
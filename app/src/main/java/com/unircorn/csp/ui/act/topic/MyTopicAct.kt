package com.unircorn.csp.ui.act.topic

import com.unircorn.csp.ui.base.BaseAct
import com.unircorn.csp.ui.fra.topic.CreateTopicFra
import com.unircorn.csp.ui.fra.topic.MyTopicFra

class MyTopicAct : BaseAct() {

    override fun createFragment() = MyTopicFra()

}
package com.unircorn.csp.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.unircorn.csp.R
import com.unircorn.csp.app.Globals
import com.unircorn.csp.app.RxBus
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.app.startAct
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.data.model.MyMenu
import com.unircorn.csp.ui.act.my.ModifyPasswordAct

class MyAdapter : BaseQuickAdapter<MyMenu, BaseViewHolder>(R.layout.item_my) {

    override fun convert(holder: BaseViewHolder, item: MyMenu) {
        holder.apply {
            setText(R.id.tvText, item.text)
            getView<View>(R.id.root).safeClicks().subscribe {
                when (item) {
                    MyMenu.Logout -> {
                        Globals.isLogout = true
                        RxBus.post(LogoutEvent(clearPassword = false))
                    }
                    MyMenu.ChangePassword -> context.startAct(ModifyPasswordAct::class.java)
                    else -> {
                    }
                }
            }
        }
    }

}
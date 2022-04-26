package com.unircorn.csp.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.data.event.ModifyDepartmentEvent
import com.unircorn.csp.data.model.MyMenu
import com.unircorn.csp.data.model.ProfileParam
import com.unircorn.csp.ui.act.StudySummaryAct2
import com.unircorn.csp.ui.act.my.ModifyPasswordAct

class MyAdapter : BaseQuickAdapter<MyMenu, BaseViewHolder>(R.layout.item_my) {

    @SuppressLint("CheckResult")
    override fun convert(holder: BaseViewHolder, item: MyMenu) {
        holder.apply {
            val text =
                if (item == MyMenu.Department) "所在部门: ${Globals.department}" else item.text
            setText(R.id.tvText, text)
            getView<View>(R.id.root).safeClicks().subscribe {
                when (item) {
                    MyMenu.Logout -> {
                        Globals.isLogout = true
                        RxBus.post(LogoutEvent(clearPassword = false))
                    }
                    MyMenu.ChangePassword -> context.startAct(ModifyPasswordAct::class.java)
                    MyMenu.ClearDownloads -> {
                        MaterialDialog(context).show {
                            title(text = "确认${MyMenu.ClearDownloads.text}?")
                            positiveButton(text = "确认") { _ ->
                                val result = FileUtils.deleteAllInDir(context.filesDir)
                                if (result) "下载文件已清空".toast()
                            }
                            negativeButton(text = "取消")
                        }
                    }
                    MyMenu.StudySummary -> context.startAct(StudySummaryAct2::class.java)
                    MyMenu.Department -> {
                        MaterialDialog(context).show {
                            input(
                                allowEmpty = false,
                                hint = "修改所在部门",
                                prefill = Globals.department
                            ) { _, text ->
                                modifyDepartment(department = text.toString())
                            }
//                            positiveButton(text = "哈哈")
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun modifyDepartment(department: String) {
        MyComponent().api.modifyProfile(ProfileParam(department = department))
            .lifeOnMain(context as LifecycleOwner)
            .subscribe(
                {
                    RxBus.post(ModifyDepartmentEvent(department = department))
                    ToastUtils.showShort("修成部门成功")
                },
                { it.errorMsg().toast() }
            )
    }

}
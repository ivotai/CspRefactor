package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.unircorn.csp.R
import com.unircorn.csp.app.MyComponent
import com.unircorn.csp.app.SpecialId
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.Training
import com.unircorn.csp.ui.act.ExaminationAct

@SuppressLint("ViewConstructor")
class DypxFooterView(private val training: Training) : FrameLayout(MyComponent().context) {

    init {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.footer_dypx, this, true)
        val tvName = root.findViewById<TextView>(R.id.tvName)
        tvName.text = "${training.name}测试"
        val tvCompleted = root.findViewById<TextView>(R.id.tvCompleted)
        tvCompleted.text = if (training.passed == 0) "未完成" else "已完成"

        root.safeClicks().subscribe {
            if (training.passed != 0) {
                ToastUtils.showShort("测试已完成")
                return@subscribe
            }
            context.startActivity(Intent(context, ExaminationAct::class.java).apply {
                putExtra(SpecialId, training.objectId)
                putExtra(Title, "${training.name}测试")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            })
        }
    }

}
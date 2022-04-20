package com.unircorn.csp.ui.fra

import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.toast
import com.unircorn.csp.data.model.TimeUnit
import com.unircorn.csp.databinding.FraStudySummaryBinding
import com.unircorn.csp.ui.base.BaseFra2
import org.joda.time.DateTime

class StudySummaryFra : BaseFra2<FraStudySummaryBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = "学习统计"
//        barChart
    s()

    }

    private  fun s(){
        val now = DateTime()
        val dateFormat = "yyyy-MM-dd"
       api.mediaPlaySummary(
           timeUnit = TimeUnit.week.toString(),
           startDate=now.minusMonths(1).toString(dateFormat),
           endDate = now.toString(dateFormat)
       )
           .lifeOnMain(this)
           .subscribe(
               {
                   if (it.failed) return@subscribe
               },
               { it.errorMsg().toast() }
           )
    }

}
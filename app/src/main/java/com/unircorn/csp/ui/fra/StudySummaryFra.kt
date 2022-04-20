package com.unircorn.csp.ui.fra

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.toast
import com.unircorn.csp.data.model.MediaPlaySummaryResponse
import com.unircorn.csp.data.model.TimeUnit
import com.unircorn.csp.databinding.FraStudySummaryBinding
import com.unircorn.csp.ui.base.BaseFra2
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList

class StudySummaryFra : BaseFra2<FraStudySummaryBinding>() {

    override fun initViews() = with(binding) {
        tvTitle.text = "学习统计"
//        barChart
        s()

    }


    private fun s() {
        val now = DateTime()
        val dateFormat = "yyyy-MM-dd"
        api.mediaPlaySummary(
            timeUnit = TimeUnit.week.toString(),
            startDate = now.minusMonths(1).toString(dateFormat),
            endDate = now.toString(dateFormat)
        )
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    chart(it.data)
                },
                { it.errorMsg().toast() }
            )
    }

    private fun chart(response: MediaPlaySummaryResponse) {
        val barEntrys = ArrayList<BarEntry>()

        response.date.forEachIndexed { dateIndex, s ->
            val floatList = ArrayList<Float>()
            response.category.forEachIndexed { categoryIndex, s ->
                floatList.add(response.value[categoryIndex][dateIndex])
            }

            val barEntry = BarEntry(dateIndex.toFloat(), floatList.toFloatArray())
            barEntrys.add(barEntry)
        }

        val set1 = BarDataSet(barEntrys, "sadf")
        set1.stackLabels = (response.category.toTypedArray())

//        val dataSets = ArrayList<IBarDataSet>()
//        dataSets.add(set1)


        val data = BarData(set1)
        binding.barChart.data = data

//        binding.barChart.setFitBars(true)
        binding.barChart.invalidate()
    }

}
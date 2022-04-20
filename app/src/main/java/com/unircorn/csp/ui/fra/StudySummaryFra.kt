package com.unircorn.csp.ui.fra

import android.graphics.Color
import com.blankj.utilcode.util.ColorUtils
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
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
        initChart()
        s()
    }

    private fun initChart() = with(binding.barChart) {
        //
        description.isEnabled = false
        // no touch
        setTouchEnabled(false)
        // 横坐标设置
        with(xAxis) {
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
        }

        setDrawValueAboveBar(false)

        // 隐藏右边坐标
        axisRight.isEnabled = false
        axisLeft.axisMinimum = 0f

        // legend
        with(legend) {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }
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

        // 展示横坐标
        binding.barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return response.date[value.toInt()]
            }
        }


        val barEntrys = ArrayList<BarEntry>()
        response.date.forEachIndexed { dateIndex, _ ->
            val floatList = ArrayList<Float>()
            response.category.forEachIndexed { categoryIndex, _ ->
                floatList.add(response.value[categoryIndex][dateIndex] / 3600)
                barEntrys.add(
                    BarEntry(
                        dateIndex.toFloat(),
                        floatList.toFloatArray()
                    )
                )
            }
        }

        val set1 = BarDataSet(barEntrys, "")
        set1.valueFormatter = object : StackedValueFormatter(true, "小时", 2) {
            override fun getBarStackedLabel(value: Float, entry: BarEntry?): String {
                if (value == 0.0f) return ""
                return super.getBarStackedLabel(value, entry)
            }

        }
        
        set1.setColors(
            ColorUtils.getColor(R.color.md_teal_400),
            ColorUtils.getColor(R.color.md_red_400),
            ColorUtils.getColor(R.color.md_blue_400),
            ColorUtils.getColor(R.color.md_cyan_400),
            ColorUtils.getColor(R.color.md_pink_400),
            ColorUtils.getColor(R.color.md_green_400)
        )
        set1.stackLabels = response.category.toTypedArray()
        set1.valueTextColor = Color.WHITE

        binding.barChart.data = BarData(set1)


//        binding.barChart.setFitBars(true)
        binding.barChart.invalidate()
    }

}
package com.unircorn.csp.ui.fra

import android.annotation.SuppressLint
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.ColorUtils
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.errorMsg
import com.unircorn.csp.app.finishAct
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.app.toast
import com.unircorn.csp.data.model.MediaPlaySummaryResponse
import com.unircorn.csp.data.model.TimeUnit
import com.unircorn.csp.databinding.FraStudySummaryBinding
import com.unircorn.csp.ui.base.BaseFra2
import org.joda.time.DateTime
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class StudySummaryFra : BaseFra2<FraStudySummaryBinding>() {

    private val dateFormat = "yyyy-MM-dd"
    private var timeUnit = TimeUnit.day.toString()
    private var startDate = DateTime().minusWeeks(1).toString(dateFormat)
    private var endDate = DateTime().toString(dateFormat)

    @SuppressLint("CheckResult")
    override fun initBindings() = with(binding) {
        ivBack.safeClicks().subscribe { finishAct() }
        ivOperation.safeClicks().subscribe {
            MaterialDialog(requireContext()).show {
                val now = DateTime()
                listItems(items = listOf("近一周", "近一月", "近一年")) { _, index, _ ->
                    when (index) {
                        0 -> {
                            timeUnit = TimeUnit.day.toString()
                            startDate = now.minusWeeks(1).toString(dateFormat)
                            endDate = now.toString(dateFormat)
                            getMediaPlaySummary()
                        }
                        1 -> {
                            timeUnit = TimeUnit.week.toString()
                            startDate = now.minusMonths(1).toString(dateFormat)
                            endDate = now.toString(dateFormat)
                            getMediaPlaySummary()
                        }
                        else -> {
                            timeUnit = TimeUnit.month.toString()
                            startDate = now.minusYears(1).toString(dateFormat)
                            endDate = now.toString(dateFormat)
                            getMediaPlaySummary()
                        }
                    }
                }
            }
        }
        getMediaPlaySummary()
    }

    override fun initViews() = with(binding) {
        tvTitle.text = "学习统计"
        initChart()
    }

    private fun initChart() = with(binding.barChart) {
        //
        description.isEnabled = false
        //
        isScaleYEnabled = false

        // 横坐标设置
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        // 隐藏右边坐标
        axisRight.isEnabled = false
        axisLeft.axisMinimum = 0f

        // legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
    }


    private fun getMediaPlaySummary() {
        api.mediaPlaySummary(
            timeUnit = timeUnit,
            startDate = startDate,
            endDate = endDate
        ).lifeOnMain(this).subscribe(
            {
                if (it.failed) return@subscribe
                refreshChart(it.data)
            },
            { it.errorMsg().toast() }
        )
    }

    private fun refreshChart(response: MediaPlaySummaryResponse) = with(binding) {

        // 横坐标显示
        binding.barChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                val index = value.toInt()
                return if (index < 0 || index > response.date.size - 1) "" else response.date[index]
            }
        }

        // create barData
        val barData = BarData()
        response.category.forEachIndexed { categoryIndex, category ->
            val barEntrys = ArrayList<BarEntry>()
            response.date.forEachIndexed { dateIndex, _ ->
                barEntrys.add(
                    BarEntry(
                        dateIndex.toFloat(),
                        response.value[categoryIndex][dateIndex] / 3600f
                    )
                )
            }
            val set = BarDataSet(barEntrys, category)
            set.color = ColorUtils.getRandomColor()
            barData.addDataSet(set)
        }

        barData.setValueFormatter(
            object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry): String {
                    // 隐藏数值为 0 的 Label
                    return if (barEntry.y == 0.0f) "" else super.getBarLabel(barEntry)
                }

                val decimalFormat = DecimalFormat("0.00")
                override fun getFormattedValue(value: Float): String {
                    return decimalFormat.format(value)
                }
            }
        )
        barChart.data = barData

        //  搞不清楚，弄不明白的分组
        val groupSpace = 0.08f
        val barSpace = 0.00f
        val barWidth = 0.92f / response.category.size.toFloat()

        // (barWidth + barSpace) * groupCount + groupSpace = 1.00 -> interval per "group"

        barData.barWidth = barWidth

        // restrict the x-axis range
        barChart.xAxis.axisMinimum = 0f
        barChart.xAxis.setCenterAxisLabels(true)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.axisMaximum = response.date.size.toFloat()
        barChart.xAxis.labelCount = response.date.size;

        //
        barChart.groupBars(0.0f, groupSpace, barSpace)
        binding.barChart.animateY(1000)
        binding.barChart.invalidate()
    }

    private val colors by lazy {
        listOf(
            ColorUtils.getColor(R.color.md_yellow_400),
            ColorUtils.getColor(R.color.md_red_400),
            ColorUtils.getColor(R.color.md_blue_400),
            ColorUtils.getColor(R.color.md_cyan_400),
            ColorUtils.getColor(R.color.md_pink_400),
            ColorUtils.getColor(R.color.md_green_400)
        )
    }

}
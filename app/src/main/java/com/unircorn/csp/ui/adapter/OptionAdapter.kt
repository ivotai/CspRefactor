package com.unircorn.csp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.unircorn.csp.R
import com.unircorn.csp.app.getAttrColor
import com.unircorn.csp.app.safeClicks
import com.unircorn.csp.data.model.base.OptionSelector
import com.unircorn.csp.databinding.ItemOptionBinding

class OptionAdapter : BaseBindingQuickAdapter<OptionSelector, ItemOptionBinding>() {

    var isQuestionSubmit: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    override fun convert(holder: BaseBindingHolder, item: OptionSelector) {
        holder.getViewBinding<ItemOptionBinding>().run {
            TvLetter.text = item.option.letter
            TvName.text = item.option.name

            TvLetter.helper.run {
                if (item.isSelected) {
                    backgroundColorNormal = context.getAttrColor(R.attr.colorPrimary)
                    textColorNormal = Color.WHITE
                    borderWidthNormal = ConvertUtils.dp2px(0f)
                } else {
                    backgroundColorNormal = Color.WHITE
                    textColorNormal = ColorUtils.getColor(R.color.md_grey_600)
                    borderWidthNormal = ConvertUtils.dp2px(1f)
                }
            }

            root.safeClicks().subscribe {
                if (isQuestionSubmit) return@subscribe

                // 单选
                data.forEach { it.isSelected = false }
                item.isSelected = true
                notifyDataSetChanged()
            }
        }
    }

}
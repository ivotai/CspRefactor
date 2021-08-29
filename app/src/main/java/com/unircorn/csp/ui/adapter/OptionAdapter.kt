package com.unircorn.csp.ui.adapter

import com.unircorn.csp.data.model.Option
import com.unircorn.csp.databinding.ItemOptionBinding

class OptionAdapter : BaseBindingQuickAdapter<Option, ItemOptionBinding>() {

    override fun convert(holder: BaseBindingHolder, item: Option) {
        holder.getViewBinding<ItemOptionBinding>().run {
            TvLetter.text = item.letter
            TvName.text = item.name
        }
    }

}
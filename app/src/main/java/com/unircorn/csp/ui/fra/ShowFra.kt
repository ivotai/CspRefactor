package com.unircorn.csp.ui.fra

import com.unircorn.csp.R
import com.unircorn.csp.databinding.FraShowBinding
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.fra.topic.TopicFra

class ShowFra : BaseFra2<FraShowBinding>() {

    override fun initViews(): Unit = with(binding) {
        super.initViews()

        tvTitle.text = "晒一晒"
        parentFragmentManager.beginTransaction().add(R.id.fragment_container_view, TopicFra())
            .commit()
    }

}
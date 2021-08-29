package com.unircorn.csp.ui.fra

import com.unircorn.csp.app.Position
import com.unircorn.csp.app.Question
import com.unircorn.csp.data.model.Question
import com.unircorn.csp.databinding.FraQuesionBinding
import com.unircorn.csp.ui.base.BaseFra2

class QuestionFra : BaseFra2<FraQuesionBinding>() {

    private val question by lazy { requireArguments().getSerializable(Question) as Question }
    private val position by lazy { requireArguments().getInt(Position)  }

}
package com.unircorn.csp.ui.act.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.ui.base.BaseAct2
import com.unircorn.csp.ui.fra.article.ArticleFra

class YzyhArticleAct : BaseAct2() {
    override fun createFragment(): Fragment {
        return ArticleFra().apply {
            arguments = Bundle().apply {
                putString(Title, "应知应会")
                putString(Category, "yzyh")
            }
        }
    }
}
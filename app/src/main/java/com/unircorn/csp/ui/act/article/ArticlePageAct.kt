package com.unircorn.csp.ui.act.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.ui.base.BaseAct2
import com.unircorn.csp.ui.fra.article.ArticlePageFra

class ArticlePageAct : BaseAct2() {

    override fun createFragment(): Fragment {
        return ArticlePageFra().apply {
            arguments = Bundle().apply {
                putString(Title, title)
                putString(Category, category)
            }
        }
    }

    private val title by lazy { intent.getStringExtra(Title) }
    private val category by lazy { intent.getStringExtra(Category) }

}
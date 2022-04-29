package com.unircorn.csp.ui.act.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.unircorn.csp.app.Category
import com.unircorn.csp.app.Title
import com.unircorn.csp.app.Training
import com.unircorn.csp.ui.base.BaseAct2
import com.unircorn.csp.ui.fra.article.ArticlePageFraForDypx

class ArticlePageActForDypx : BaseAct2() {

    override fun createFragment(): Fragment {
        return ArticlePageFraForDypx().apply {
            arguments = Bundle().apply {
                putString(Title, title)
                putString(Category, category)
                putSerializable(Training, training)
            }
        }
    }

    private val title by lazy { intent.getStringExtra(Title) }
    private val category by lazy { intent.getStringExtra(Category) }
    private val training by lazy { intent.getSerializableExtra(Training) }

}
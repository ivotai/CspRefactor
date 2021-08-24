package com.unircorn.csp.ui.fra

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.hjq.bar.OnTitleBarListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.databinding.FraMainBinding
import com.unircorn.csp.ui.act.LoginAct
import com.unircorn.csp.ui.act.article.ArticleSearchAct
import com.unircorn.csp.ui.act.my.MyAct
import com.unircorn.csp.ui.base.BaseFra
import com.unircorn.csp.ui.fraStateAdapter.MainFraStateAdapter
import io.reactivex.rxjava3.functions.Consumer
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.item.NormalItemView

class MainFra : BaseFra() {

    override fun initViews() {
        initViewPager2()
        initTab()
    }

    private fun initViewPager2() = with(binding.viewPager2) {
        removeEdgeEffect()
        isUserInputEnabled = false
        offscreenPageLimit = MainFraStateAdapter.titles.size - 1
        adapter = MainFraStateAdapter(this@MainFra)
    }

    private lateinit var navigationController: NavigationController

    private fun initTab() = with(binding) {
        navigationController = tab.custom()
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_graduation_cap,
                    MainFraStateAdapter.abbr[0]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_comments1,
                    MainFraStateAdapter.abbr[1]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_balance_scale,
                    MainFraStateAdapter.abbr[2]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_calendar_check1,
                    MainFraStateAdapter.abbr[3]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book_open,
                    MainFraStateAdapter.abbr[4]
                )
            )
            .build()
    }

    private fun newItem(icon: IIcon, text: String) =
        NormalItemView(requireContext()).apply {
            initialize(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, text)
            setDefaultDrawable(
                IconicsDrawable(requireContext(), icon).apply {
                    colorInt = defaultColor
                    sizeDp = 24
                }
            )
            setSelectedDrawable(
                IconicsDrawable(requireContext(), icon).apply {
                    colorInt = checkedColor
                    sizeDp = 24
                }
            )
            setTextDefaultColor(defaultColor)
            setTextCheckedColor(checkedColor)
        }

    // 这个颜色有待商榷
    private val defaultColor = ColorUtils.getColor(R.color.md_grey_600)
    private val checkedColor = ColorUtils.getColor(R.color.md_red_700)

    override fun initBindings() = with(binding) {
        navigationController.addSimpleTabItemSelectedListener { index, _ ->
//            titleBar.title = MainFraStateAdapter.titles[index]
            viewPager2.setCurrentItem(index, false)
        }
    }

    override fun initEvents() {
        fun logout(logoutEvent: LogoutEvent) {
            api.logout()
                .lifeOnMain(this)
                .subscribe(
                    { response ->
                        if (response.failed) return@subscribe
                        ActivityUtils.finishAllActivities()
                        Intent(requireContext(), LoginAct::class.java).apply {
                            putExtra(Param, logoutEvent.clearPassword)
                        }.let { startActivity(it) }
                    },
                    { it.errorMsg().toast() }
                )
        }
        RxBus.registerEvent(this, LogoutEvent::class.java, Consumer {
            logout(it)
        })
    }

    // ----

    private var _binding: FraMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.unircorn.csp.ui.fra

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unicorn.sanre.icon.Fas
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.databinding.FraMainBinding
import com.unircorn.csp.ui.act.LoginAct2
import com.unircorn.csp.ui.base.BaseFra2
import com.unircorn.csp.ui.pagerAdapter.MainPagerAdapter
import io.reactivex.rxjava3.functions.Consumer
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.item.NormalItemView

class MainFra2 : BaseFra2<FraMainBinding>() {

    override fun initViews() {
        initViewPager2()
        initTab()
    }

    private fun initViewPager2() = with(binding.viewPager2) {
        isUserInputEnabled = false
        offscreenPageLimit = MainPagerAdapter.abbr.size - 1
        adapter = MainPagerAdapter(this@MainFra2)
        removeEdgeEffect()
    }

    private lateinit var navigationController: NavigationController

    private fun initTab() = with(binding) {
        navigationController = tab.custom()
            .addItem(
                newItem(
                    Fas.Icon.fas_graduation_cap,
                    MainPagerAdapter.abbr[0]
                )
            )
            .addItem(
                newItem(
                    Fas.Icon.fas_balance_scale,
                    MainPagerAdapter.abbr[1]
                )
            )
            .addItem(
                newItem(
                    Fas.Icon.fas_calendar_check,
                    MainPagerAdapter.abbr[2]
                )
            )
            .addItem(
                newItem(
                    Fas.Icon.fas_book_open,
                    MainPagerAdapter.abbr[3]
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
            viewPager2.setCurrentItem(
                index,
                false
            )
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
                        Intent(requireContext(), LoginAct2::class.java).apply {
                            putExtra(Param, logoutEvent.clearPassword)
                        }.let { startActivity(it) }
                    },
                    {
                        it.toast()
                    }
                )
        }
        RxBus.registerEvent(this, LogoutEvent::class.java, Consumer {
            logout(it)
        })
    }

}
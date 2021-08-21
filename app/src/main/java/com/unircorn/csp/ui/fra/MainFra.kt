package com.unircorn.csp.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ColorUtils
import com.hjq.bar.OnTitleBarListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.unircorn.csp.R
import com.unircorn.csp.app.removeEdgeEffect
import com.unircorn.csp.app.startAct
import com.unircorn.csp.databinding.FraMainBinding
import com.unircorn.csp.ui.act.MyAct
import com.unircorn.csp.ui.base.BaseFra
import com.unircorn.csp.ui.fragmentStateAdapter.MainFragmentStateAdapter
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.item.NormalItemView

class MainFra : BaseFra(R.layout.fra_main) {

    override fun initViews() {
        initViewPager2()
        initTab()
    }

    private fun initViewPager2() = with(binding.viewPager2) {
        removeEdgeEffect()
        isUserInputEnabled = false
        offscreenPageLimit = MainFragmentStateAdapter.titles.size - 1
        adapter = MainFragmentStateAdapter(this@MainFra)
    }

    private lateinit var navigationController: NavigationController

    private fun initTab() = with(binding) {
        navigationController = tab.custom()
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_graduation_cap,
                    MainFragmentStateAdapter.abbr[0]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_comments1,
                    MainFragmentStateAdapter.abbr[1]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_balance_scale,
                    MainFragmentStateAdapter.abbr[2]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_calendar_check1,
                    MainFragmentStateAdapter.abbr[3]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book_open,
                    MainFragmentStateAdapter.abbr[4]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_book,
                    MainFragmentStateAdapter.abbr[5]
                )
            )
            .addItem(
                newItem(
                    FontAwesome.Icon.faw_cog,
                    MainFragmentStateAdapter.abbr[6]
                )
            )
            .build()
        titleBar.title = MainFragmentStateAdapter.titles[0]
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

    private val defaultColor = ColorUtils.getColor(R.color.md_grey_600)
    private val checkedColor = ColorUtils.getColor(R.color.md_red_700)

    override fun initBindings() {
        with(binding) {
            navigationController.addSimpleTabItemSelectedListener { index, _ ->
                titleBar.title = MainFragmentStateAdapter.titles[index]
            }
            titleBar.setOnTitleBarListener(object : OnTitleBarListener {
                override fun onLeftClick(view: View?) {
                    // todo 搜索
                }

                override fun onTitleClick(view: View?) {

                }

                override fun onRightClick(view: View?) {
                    startAct(MyAct::class.java)
                }

            })
        }
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
package com.unircorn.csp.ui.fra.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.data.model.ModifyPasswordParam
import com.unircorn.csp.databinding.FraModifyPasswordBinding
import com.unircorn.csp.ui.act.LoginAct2
import com.unircorn.csp.ui.base.BaseFra

class ModifyPasswordFra : BaseFra() {

    override fun initBindings() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                requireActivity().finish()
            }

            override fun onRightClick(v: View?) {
                changePasswordX()
            }

            override fun onTitleClick(v: View?) {
            }
        })
    }

    private fun changePasswordX() = with(binding) {
        fun changePassword() {
            api.modifyPassword(
                ModifyPasswordParam(
                    originPassword = etOldPwd.trimText(),
                    newPassword = etNewPwd.trimText()
                )
            )
                .lifeOnMain(this@ModifyPasswordFra)
                .subscribe(
                    {
                        if (it.failed) return@subscribe
                        ToastUtils.showShort("修改密码成功，请重新登录")
                        // 因为登录时因为密码太简单，无法把 LogoutEvent 发给 MainFra2，所以自己来 Logout
                        Globals.isLogout = true
                        logout(LogoutEvent(clearPassword = true))
                    },
                    { it.errorMsg().toast() }
                )
        }

        if (etOldPwd.isEmpty()) {
            ToastUtils.showShort("旧密码不能为空")
            return
        }
        if (etNewPwd.isEmpty()) {
            ToastUtils.showShort("新密码不能为空")
            return
        }
        if (etConfirmPwd.isEmpty()) {
            ToastUtils.showShort("确认密码不能为空")
            return
        }
        if (!isPwdValid(etNewPwd.trimText())) {
            ToastUtils.showShort("密码至少包含大写字母、小写字母、数字、特殊字符的其中三种，长度8-16位")
            return
        }
        if (etConfirmPwd.trimText() != etNewPwd.trimText()) {
            ToastUtils.showShort("密码不一致")
            return
        }
        changePassword()
    }

    private fun isPwdValid(pwd: String): Boolean {
        val pattern =
            "^(?![a-zA-Z]+\$)(?![A-Z0-9]+\$)(?![A-Z\\W_!@#\$%^&*`~()-+=]+\$)(?![a-z0-9]+\$)(?![a-z\\W_!@#\$%^&*`~()-+=]+\$)(?![0-9\\W_!@#\$%^&*`~()-+=]+\$)[a-zA-Z0-9\\W_!@#\$%^&*`~()-+=]{8,16}\$"
        val regex = pattern.toRegex()
        return regex.matches(pwd)
    }

    private fun logout(logoutEvent: LogoutEvent) {
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


    // ----

    private var _binding: FraModifyPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraModifyPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.unircorn.csp.ui.fra.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.RxBus
import com.unircorn.csp.app.isEmpty
import com.unircorn.csp.app.toast
import com.unircorn.csp.app.trimText
import com.unircorn.csp.data.event.LogoutEvent
import com.unircorn.csp.data.model.ModifyPasswordParam
import com.unircorn.csp.databinding.FraModifyPasswordBinding
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
                        RxBus.post(LogoutEvent(clearPassword = true))
                    },
                    { it.toast() }
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
        if (etConfirmPwd.trimText() != etNewPwd.trimText()) {
            ToastUtils.showShort("密码不一致")
            return
        }
        changePassword()
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
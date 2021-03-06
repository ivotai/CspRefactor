package com.unircorn.csp.ui.fra

import android.Manifest
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.tbruyelle.rxpermissions3.RxPermissions
import com.unicorn.sanre.icon.Fas
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.VersionHelper
import com.unircorn.csp.databinding.FraLoginBinding
import com.unircorn.csp.ui.base.BaseFra2

class LoginFra2 : BaseFra2<FraLoginBinding>() {

    override fun initViews() = with(binding) {
        tilUsername.startIconDrawable =
            IconicsDrawable(requireContext(), Fas.Icon.fas_user).apply {
                sizeDp = 24
            }
        tilPassword.startIconDrawable =
            IconicsDrawable(requireContext(), Fas.Icon.fas_lock).apply {
                sizeDp = 24
            }
        with(UserInfo) {
            etUsername.setText(username)
            etPassword.setText(password)
        }

        val clearPassword = arguments?.getBoolean(Param, false) ?: false
        if (clearPassword) {
            etPassword.setText("")
            etPassword.requestFocus()
        }
    }

    override fun initBindings() = with(binding) {
        btnLogin.safeClicks().subscribe { loginX() }
        requestPermissions()
    }

    private fun requestPermissions() {
        fun autoLogin() {
            if (UserInfo.username.isNotEmpty() && !Globals.isLogout) loginX()
        }

        RxPermissions(this)
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .subscribe { granted ->
                if (!granted)
                    finishAct()
                else {
                    autoLogin()
                }
            }
    }

    private fun loginX() = with(binding) {
        if (etUsername.isEmpty()) {
            ("账号不能为空").toast()
            return@with
        }
        if (etPassword.isEmpty()) {
            ("密码不能为空").toast()
            return@with
        }
        login()
    }

    private fun login() = with(binding) {
        api.login(etUsername.trimText(), etPassword.trimText())
            .lifeOnMain(this@LoginFra2)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    Globals.loginResponse = it
                    Globals.isLogout = false
                    with(UserInfo) {
                        username = etUsername.trimText()
                        password = etPassword.trimText()
                    }
                    VersionHelper.checkVersion(requireActivity())
                },
                {
                    it.toast()
                }
            )
    }

}
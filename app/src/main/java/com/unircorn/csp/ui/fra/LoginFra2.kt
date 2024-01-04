package com.unircorn.csp.ui.fra

import android.Manifest
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.tbruyelle.rxpermissions3.RxPermissions
import com.unicorn.sanre.icon.Fas
import com.unircorn.csp.app.*
import com.unircorn.csp.app.UserInfo.password
import com.unircorn.csp.app.UserInfo.username
import com.unircorn.csp.app.helper.VersionHelper
import com.unircorn.csp.databinding.FraLoginBinding
import com.unircorn.csp.ui.act.my.ModifyPasswordAct
import com.unircorn.csp.ui.base.BaseFra2
import java.util.Base64

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
        val originalUsername = etUsername.trimText()
        val originalPassword = etPassword.trimText()
        val key = "GVUPOU3rO4oStHw59gmwVkzMwPhZMfaJzWBwwESiVoE="
        val iv = "NU8dqRenATFfrEhekZ5hDA=="
        val base64Key = EncodeUtils.base64Decode(key)
        val base64iv = EncodeUtils.base64Decode(iv)
        val encryptedUsername = EncryptUtils.encryptAES2Base64(originalUsername.toByteArray(), base64Key, "AES/CBC/PKCS7Padding", base64iv)
        val encryptedPassword = EncryptUtils.encryptAES2Base64(originalPassword.toByteArray(), base64Key, "AES/CBC/PKCS7Padding", base64iv)
        api.login(String(encryptedUsername), String(encryptedPassword))
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

                    // 如果密码太简单
                    if (it.weakCipher) {
                        ToastUtils.showLong("密码至少同时包含大写字母、小写字母、数字和特殊字符，长度8-16位")
                        startAct(ModifyPasswordAct::class.java)
                    } else
                        VersionHelper.checkVersion(requireActivity())
                },
                {
                    it.toast()
                }
            )
    }

}
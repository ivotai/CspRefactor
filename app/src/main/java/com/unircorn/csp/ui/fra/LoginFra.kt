package com.unircorn.csp.ui.fra

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.tbruyelle.rxpermissions3.RxPermissions
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.VersionHelper
import com.unircorn.csp.databinding.FraLoginBinding
import com.unircorn.csp.ui.base.BaseFra

class LoginFra : BaseFra() {

    override fun initViews() = with(binding) {
        tilUsername.startIconDrawable =
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_user1).apply {
                sizeDp = 24
            }
        tilPassword.startIconDrawable =
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_lock).apply {
                sizeDp = 24
            }
        with(UserInfo) {
            etUsername.setText(username)
            etPassword.setText(password)
        }
        if (fromModifyPassword) {
            etPassword.setText("")
            etPassword.requestFocus()
        }
    }

    private val fromModifyPassword by lazy { arguments?.getBoolean(Param, false) ?: false }

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
            .lifeOnMain(this@LoginFra)
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
                { it.toast() }
            )
    }

    // ----

    private var _binding: FraLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
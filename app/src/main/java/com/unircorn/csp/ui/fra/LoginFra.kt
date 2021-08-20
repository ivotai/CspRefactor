package com.unircorn.csp.ui.fra

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import com.unircorn.csp.R
import com.unircorn.csp.databinding.FraLoginBinding
import com.unircorn.csp.ui.base.BaseFra

class LoginFra : BaseFra(R.layout.fra_login) {

    override fun initViews() {
        val i =IconicsDrawable(requireContext(), FontAwesome.Icon.faw_android).apply {
            colorInt = Color.RED
            sizeDp = 24
        }
        binding.textField.startIconDrawable = i

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
package com.unircorn.csp.ui.fra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unircorn.csp.R
import com.unircorn.csp.databinding.FraMyBinding
import com.unircorn.csp.ui.base.BaseFra

class MyFra : BaseFra(R.layout.fra_my) {


    // ----

    private var _binding: FraMyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
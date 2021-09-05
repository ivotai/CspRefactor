package com.unircorn.csp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.jzvd.Jzvd
import com.dylanc.viewbinding.base.inflateBindingWithGeneric
import com.unircorn.csp.app.MyComponent


abstract class BaseFra2<VB : ViewBinding> : Fragment(), UI {

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        initBindings()
        initEvents()
    }

    override fun initViews() = with(binding) {
    }

    override fun initBindings(): Unit {
    }

    override fun initEvents() {
    }

    val api by lazy { MyComponent().api }

    //

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBindingWithGeneric(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}


package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.unircorn.csp.R
import com.unircorn.csp.app.third.GlideEngine
import com.unircorn.csp.databinding.FraCreateTopicBinding
import com.unircorn.csp.ui.base.BaseFra

class CreateTopicFra : BaseFra(R.layout.fra_create_topic) {

    private fun takeVideo(){
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofVideo())
            .imageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: List<LocalMedia?>) {
                    val realPath = result[0]!!.realPath
                    PictureSelector.create(this@CreateTopicFra).externalPictureVideo(realPath);
                }

                override fun onCancel() {
                    // onCancel Callback
                }
            })
    }

    //

    private var _binding: FraCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraCreateTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
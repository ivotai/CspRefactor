package com.unircorn.csp.ui.fra.topic

import android.media.ThumbnailUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.kaopiz.kprogresshud.KProgressHUD
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.app.third.GlideEngine
import com.unircorn.csp.data.event.RefreshTopicEvent
import com.unircorn.csp.data.model.CreateTopicParam
import com.unircorn.csp.data.model.UploadResponse
import com.unircorn.csp.databinding.FraCreateTopicBinding
import com.unircorn.csp.ui.base.BaseFra
import rxhttp.RxHttp
import java.io.File

class CreateTopicFra : BaseFra(R.layout.fra_create_topic) {

    override fun initViews() {
        super.initViews()
        initFab()
    }

    private fun initFab() {
        binding.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_video).apply {
                sizeDp = 24
            }
        )
    }

    private fun takeVideo() {
//        PictureSelector.create(this)
//            .openCamera(PictureMimeType.ofVideo())
//            .imageEngine(GlideEngine.createGlideEngine())
//            .forResult(object : OnResultCallbackListener<LocalMedia?> {
//                override fun onResult(result: List<LocalMedia?>) {
//                    val realPath = result[0]!!.realPath
//                    uploadVideo(realPath)
//                }
//
//                override fun onCancel() {
//                    // onCancel Callback
//                }
//            })

        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofVideo())
            .imageEngine(GlideEngine.createGlideEngine())
            .videoMaxSecond(15)
            .videoMinSecond(5)
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: List<LocalMedia?>) {
                    val realPath = result[0]!!.realPath
                    uploadVideo(realPath)
                    // onResult Callback
                }

                override fun onCancel() {
                    // onCancel Callback
                }
            })
    }

    private fun uploadVideo(path: String) {
        val progressMask = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        RxHttp.postForm(uploadUrl)
            .addFile(attachment, File(path))
            .upload { progressMask.setProgress(it.progress) }
            .asClass(UploadResponse::class.java).subscribe({
                progressMask.dismiss()
                createTopicParam.videos = listOf(it)
                "视频已上传".toast()
            }, {
                progressMask.dismiss()
                it.toast()
            })
    }

    override fun initBindings() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                requireActivity().finish()
            }

            override fun onRightClick(v: View?) {
                createTopicX()
            }

            override fun onTitleClick(v: View?) {
            }
        })
        binding.floatingActionButton.safeClicks().subscribe {
            takeVideo()
        }
    }

    private fun createTopicX() = with(binding) {
        if (etTitle.isEmpty()) {
            ToastUtils.showShort("标题不能为空")
            return@with
        }
        if (etContent.isEmpty()) {
            ToastUtils.showShort("内容不能为空")
            return@with
        }
        createTopic()
    }

    private fun createTopic() = with(binding) {
        createTopicParam.title = etTitle.trimText()
        createTopicParam.content = etContent.trimText()
        api.createTopic(createTopicParam)
            .lifeOnMain(this@CreateTopicFra)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    ToastUtils.showShort("发帖成功")
                    RxBus.post(RefreshTopicEvent())
                    requireActivity().finish()
                },
                { it.toast() }
            )
    }

    private val createTopicParam = CreateTopicParam()
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
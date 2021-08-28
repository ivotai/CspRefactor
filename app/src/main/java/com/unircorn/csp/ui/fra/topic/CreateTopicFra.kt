package com.unircorn.csp.ui.fra.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.hjq.bar.OnTitleBarListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.app.helper.ProgressHelper
import com.unircorn.csp.app.third.GlideEngine
import com.unircorn.csp.data.event.RefreshTopicEvent
import com.unircorn.csp.data.model.CreateTopicParam
import com.unircorn.csp.data.model.UploadResponse
import com.unircorn.csp.databinding.FraCreateTopicBinding
import com.unircorn.csp.ui.base.BaseFra
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import rxhttp.RxHttp
import java.io.File

class CreateTopicFra : BaseFra() {

    override fun initViews() {
        super.initViews()
        initFloatingActionButton()
    }

    private fun initFloatingActionButton() {
        binding.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_video).apply {
                sizeDp = 24
            }
        )
    }

    override fun initBindings() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finishAct()
            }

            override fun onRightClick(v: View?) {
                createTopicX()
            }

            override fun onTitleClick(v: View?) {
            }
        })
        binding.floatingActionButton.safeClicks().subscribe { selectPicture() }
    }

    private fun selectPicture() {
        PictureSelector.create(this)
            .openGallery(if (justVideo) PictureMimeType.ofVideo() else PictureMimeType.ofAll())
            .imageEngine(GlideEngine.createGlideEngine())
            .videoMaxSecond(15)
            .maxSelectNum(5)
            .isCompress(true)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: List<LocalMedia>) {
                    upload(result)
                }

                override fun onCancel() {
                    // onCancel Callback
                }
            })
    }

    private fun upload(result: List<LocalMedia>) {
        val isImage = result[0].mimeType == "image/jpeg"

        val progressMask = ProgressHelper.showMask(requireActivity())
        RxHttp.postForm(uploadUrl)
            .addFiles(
                attachments,
                result.map { File(if (isImage) it.compressPath else it.realPath) })
//            .addParts(requireContext(), attachments, result.map { Uri.parse(it.path) })
            .upload(AndroidSchedulers.mainThread()) {
                progressMask.setProgress(it.progress)
            }
            .asList(UploadResponse::class.java).subscribe({
                progressMask.dismiss()
                if (isImage) {
                    createTopicParam.images = it
                } else {
                    createTopicParam.videos = it
                }
                "上传成功".toast()
            }, {
                progressMask.dismiss()
                it.errorMsg().toast()
            })
    }

    //

    private fun createTopicX() = with(binding) {
        if (etTitle.isEmpty()) {
            "标题不能为空".toast()
            return@with
        }
        if (etContent.isEmpty()) {
            "内容不能为空".toast()
            return@with
        }
        if (justVideo && createTopicParam.videos.isEmpty()) {
            "请上传视频".toast()
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
                    finishAct()
                },
                {
                    it.errorMsg().toast()
                }
            )
    }

    private val createTopicParam = CreateTopicParam()

    private val justVideo by lazy { requireArguments().getBoolean(Param) }

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
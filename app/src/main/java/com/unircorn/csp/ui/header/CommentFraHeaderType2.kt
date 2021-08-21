package com.unircorn.csp.ui.header

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.blankj.utilcode.util.ToastUtils
import com.github.barteksc.pdfviewer.PDFView
import com.kaopiz.kprogresshud.KProgressHUD
import com.unircorn.csp.R
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.Article
import com.unircorn.csp.data.model.Attachment
import rxhttp.RxHttp
import rxhttp.wrapper.exception.HttpStatusCodeException

@SuppressLint("ViewConstructor")
class CommentFraHeaderType2(context:Context,private val article: Article) : FrameLayout(context) {

    init {
        initViews()
    }

    private fun initViews() {
        val root =
            LayoutInflater.from(context).inflate(R.layout.header_fra_comment_type2, this, true)
        val pdfView = root.findViewById<PDFView>(R.id.pdfView)
        val pdf = article.pdf
        if (pdf.exists) pdfView.fromFile(pdf.file).load()
        else download(pdfView, pdf)
    }

    private fun download(pdfView: PDFView, pdf: Attachment) {
        val progressMask = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val url = baseUrl + pdf.url
        RxHttp.get(url)
            .addHeader(Cookie, "$SESSION=${Globals.session}")
            .asDownload(pdf.path) { progressMask.setProgress(it.progress) }
            .subscribe(
                {
                    progressMask.dismiss()
                    pdfView.fromFile(pdf.file).load()
                },
                {
                    progressMask.dismiss()
                    if (it is HttpStatusCodeException) {
                        if (it.statusCode == "401")
                            "登陆超时，请重新登陆".toast()
                    }
                }
            )
    }

}
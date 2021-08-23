package com.unircorn.csp.ui.fra.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.hjq.bar.TitleBar
import com.unircorn.csp.app.Cookie
import com.unircorn.csp.app.Globals
import com.unircorn.csp.app.SESSION
import com.unircorn.csp.app.helper.ProgressHelper
import com.unircorn.csp.app.toast
import com.unircorn.csp.data.model.Attachment
import com.unircorn.csp.databinding.FraArticleDetailPdfBinding
import com.unircorn.csp.ui.base.ArticleDetailFra
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import rxhttp.RxHttp
import rxhttp.wrapper.exception.HttpStatusCodeException

class ArticleDetailPdfFra : ArticleDetailFra() {

    override fun initViews() {
        super.initViews()
        initPdfView()
    }

    private fun initPdfView() = with(binding) {
        fun download(pdfView: PDFView, pdf: Attachment) {
            val progressMask = ProgressHelper.showMask(requireActivity())
            RxHttp.get(pdf.fullUrl)
                .addHeader(Cookie, "$SESSION=${Globals.session}")
                .asDownload(pdf.path, AndroidSchedulers.mainThread()) {
                    progressMask.setProgress(it.progress)
                }
                .subscribe(
                    {
                        progressMask.dismiss()
                        pdfView.fromFile(pdf.file).load()
                    },
                    {
                        progressMask.dismiss()
                        if (it is HttpStatusCodeException) {
                            if (it.statusCode == "401")
                            // todo
                                "登陆超时，请重新登陆".toast()
                        }
                    }
                )
        }

        val pdf = article.pdf
        if (pdf.exists) pdfView.fromFile(pdf.file).load()
        else download(pdfView, pdf)
    }

    override val titleBar: TitleBar
        get() = binding.titleBar

    override val etContent: TextInputEditText
        get() = binding.etContent

    override val btnCreateComment: MaterialButton
        get() = binding.btnCreateComment
    override val mRecyclerView: RecyclerView
        get() = binding.recyclerView

    override val mSwipeRefreshLayout: SwipeRefreshLayout
        get() = binding.swipeRefreshLayout

// ----

    private var _binding: FraArticleDetailPdfBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FraArticleDetailPdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
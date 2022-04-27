package com.unircorn.csp.ui.base

import com.rxjava.rxlife.lifeOnMain
import com.unircorn.csp.app.*
import com.unircorn.csp.data.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class MediaOwnerFra : CommentPageFra() {

    abstract fun getMediaOwnerId(): String

    override fun initBindings() {
        super.initBindings()
        createMediaPlay()
    }

    private var mediaPlayId = ""

    private fun createMediaPlay() {
        api.createMediaPlay(articleId = getMediaOwnerId())
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                    mediaPlayId = it.data.mediaPlayId

                    Observable.interval(1, TimeUnit.MINUTES)
                        .lifeOnMain(this)
                        .subscribe { keepMediaPlay() }
                },
                { it.errorMsg().toast() }
            )
    }

    private fun keepMediaPlay() {
        if (mediaPlayId.isEmpty()) return
        api.mediaPlayStatus(
            articleId = getMediaOwnerId(),
            mediaPlayStatus = MediaPlayStatus(mediaPlayId = mediaPlayId, type = 1)
        )
            .lifeOnMain(this)
            .subscribe(
                {
                    if (it.failed) return@subscribe
                },
                { it.errorMsg().toast() }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        finishMediaPlay()
    }

    private fun finishMediaPlay() {
        if (mediaPlayId.isEmpty()) return
        api.mediaPlayStatus(
            articleId = getMediaOwnerId(),
            mediaPlayStatus = MediaPlayStatus(mediaPlayId = mediaPlayId, type = 2)
        )
            // 没有用 onMain 没有用自动取消
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it.failed) return@subscribe
                },
                { it.errorMsg().toast() }
            )
    }

}
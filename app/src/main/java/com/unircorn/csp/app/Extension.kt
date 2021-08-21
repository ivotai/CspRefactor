package com.unircorn.csp.app

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

fun View.safeClicks(): Observable<Unit> = this.clicks()
    .throttleFirst(2, TimeUnit.SECONDS)

fun Context.startAct(cls: Class<*>) = startActivity(Intent(this, cls))

fun Fragment.startAct(cls: Class<*>, finishAct: Boolean = false) {
    requireActivity().startAct(cls)
    if (finishAct) activity?.finish()
}

fun TextView.trimText() = text.toString().trim()

fun TextView.isEmpty(): Boolean = trimText().isEmpty()

fun String?.toast() = this.let { ToastUtils.showShort(it) }

fun ViewPager2.removeEdgeEffect() {
    (this.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
}

fun Throwable.toast() = fun(){
    val message = when (this) {
        is UnknownHostException -> "接口地址设置有误或无网络"
        is SocketTimeoutException -> "超时了"
        is HttpException -> when (code()) {
            500 -> "服务器内部错误"
            else -> "错误码${code()}"
        }
        else -> toString()
    }
    message.toast()
}


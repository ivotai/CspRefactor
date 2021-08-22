package com.unircorn.csp.app.helper;

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.UriUtils
import com.blankj.utilcode.util.Utils
import java.io.File

class FileUtils2 {

    companion object {

        fun openFile(context: Context, file: File) {
            try {
                val intent = Intent()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //设置intent的Action属性
                intent.action = Intent.ACTION_VIEW
                //获取文件file的MIME类型
                val type = getMIMEType(file)

                val data:Uri = UriUtils.file2Uri(file)
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                    data = Uri.fromFile(file)
//                } else {
//                    val authority = Utils.getApp()
//                        .packageName + ".utilcode.provider"
//                    data = FileProvider.getUriForFile(
//                        Utils.getApp(),
//                        authority,
//                        file
//                    )
//                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//                }
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                //设置intent的data和Type属性。
                intent.setDataAndType(/*uri*/data, type)
                //跳转
                context.startActivity(intent)
                //	    Intent.createChooser(intent, "请选择对应的软件打开该附件！");
            } catch (e: ActivityNotFoundException) {
                ToastUtils.showShort("未找到打开附件的第三方应用")
            }

        }

        private fun getMIMEType(file: File): String {

            var type = "*/*"
            val fName = file.getName()
            //获取后缀名前的分隔符"."在fName中的位置。
            val dotIndex = fName.lastIndexOf(".")
            if (dotIndex < 0) {
                return type
            }
            /* 获取文件的后缀名*/
            val end = fName.substring(dotIndex, fName.length).toLowerCase()
            if (end === "") return type
            //在MIME和文件类型的匹配表中找到对应的MIME类型。
            for (i in 0 until MIME_MapTable.size) {

                if (end == MIME_MapTable[i][0])
                    type = MIME_MapTable[i][1]
            }
            return type
        }

        private val MIME_MapTable = arrayOf(
            arrayOf(".3gp", "video/3gpp"),
            arrayOf(".apk", "application/vnd.android.package-archive"),
            arrayOf(".asf", "video/x-ms-asf"),
            arrayOf(".avi", "video/x-msvideo"),
            arrayOf(".bin", "application/octet-stream"),
            arrayOf(".bmp", "image/bmp"),
            arrayOf(".c", "text/plain"),
            arrayOf(".class", "application/octet-stream"),
            arrayOf(".conf", "text/plain"),
            arrayOf(".cpp", "text/plain"),
            arrayOf(".doc", "application/msword"),
            arrayOf(
                ".docx",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            ),
            arrayOf(".xls", "application/vnd.ms-excel"),
            arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            arrayOf(".exe", "application/octet-stream"),
            arrayOf(".gif", "image/gif"),
            arrayOf(".gtar", "application/x-gtar"),
            arrayOf(".gz", "application/x-gzip"),
            arrayOf(".h", "text/plain"),
            arrayOf(".htm", "text/html"),
            arrayOf(".html", "text/html"),
            arrayOf(".jar", "application/java-archive"),
            arrayOf(".java", "text/plain"),
            arrayOf(".jpeg", "image/jpeg"),
            arrayOf(".jpg", "image/jpeg"),
            arrayOf(".js", "application/x-javascript"),
            arrayOf(".log", "text/plain"),
            arrayOf(".m3u", "audio/x-mpegurl"),
            arrayOf(".m4a", "audio/mp4a-latm"),
            arrayOf(".m4b", "audio/mp4a-latm"),
            arrayOf(".m4p", "audio/mp4a-latm"),
            arrayOf(".m4u", "video/vnd.mpegurl"),
            arrayOf(".m4v", "video/x-m4v"),
            arrayOf(".mov", "video/quicktime"),
            arrayOf(".mp2", "audio/x-mpeg"),
            arrayOf(".mp3", "audio/x-mpeg"),
            arrayOf(".mp4", "video/mp4"),
            arrayOf(".mpc", "application/vnd.mpohun.certificate"),
            arrayOf(".mpe", "video/mpeg"),
            arrayOf(".mpeg", "video/mpeg"),
            arrayOf(".mpg", "video/mpeg"),
            arrayOf(".mpg4", "video/mp4"),
            arrayOf(".mpga", "audio/mpeg"),
            arrayOf(".msg", "application/vnd.ms-outlook"),
            arrayOf(".ogg", "audio/ogg"),
            arrayOf(".pdf", "application/pdf"),
            arrayOf(".png", "image/png"),
            arrayOf(".pps", "application/vnd.ms-powerpoint"),
            arrayOf(".ppt", "application/vnd.ms-powerpoint"),
            arrayOf(
                ".pptx",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            ),
            arrayOf(".prop", "text/plain"),
            arrayOf(".rc", "text/plain"),
            arrayOf(".rmvb", "audio/x-pn-realaudio"),
            arrayOf(".rtf", "application/rtf"),
            arrayOf(".sh", "text/plain"),
            arrayOf(".tar", "application/x-tar"),
            arrayOf(".tgz", "application/x-compressed"),
            arrayOf(".txt", "text/plain"),
            arrayOf(".wav", "audio/x-wav"),
            arrayOf(".wma", "audio/x-ms-wma"),
            arrayOf(".wmv", "audio/x-ms-wmv"),
            arrayOf(".wps", "application/vnd.ms-works"),
            arrayOf(".xml", "text/plain"),
            arrayOf(".z", "application/x-compress"),
            arrayOf(".zip", "application/x-zip-compressed"),
            arrayOf("", "*/*")
        )


    }


}
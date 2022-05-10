package com.example.instagram.common.util

import android.app.Activity
import com.example.instagram.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Files {

    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-sss"

    fun generateFile(activity: Activity): File {
        val midiaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        val outpuDir = if (midiaDir != null && midiaDir.exists())
            midiaDir else activity.filesDir
        return File(
            outpuDir,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(
                System.currentTimeMillis()) + ".jpg"
        )
    }
}
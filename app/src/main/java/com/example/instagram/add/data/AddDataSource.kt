package com.example.instagram.add.data

import android.net.Uri
import androidx.camera.core.ImageCapture
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException

interface AddDataSource {
    fun createPost(userUUID : String,uri:Uri,caption : String,
                   callback: RequestCallback<Boolean>){throw UnsupportedOperationException()}
    fun fetchSession(): UserAuth {throw UnsupportedOperationException() }
}
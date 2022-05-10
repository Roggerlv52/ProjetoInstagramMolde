package com.example.instagram.add

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Add {
    interface Presenter : BasePresenter {
        fun createPost(uri: Uri, caption: String)
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequesteSuccess()
        fun displayRequesteFailure(message: String)


    }
}
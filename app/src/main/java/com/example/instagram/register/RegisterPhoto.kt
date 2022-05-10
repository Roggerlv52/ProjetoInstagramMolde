package com.example.instagram.register

import android.net.Uri
import androidx.annotation.StringRes
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface RegisterPhoto {
    interface Presenter : BasePresenter {
        fun updateUser(photo: Uri)
    }
    interface view : BaseView<Presenter> {
        //criar caso de usos
        fun showProgress(enable : Boolean)

        fun onUpdateFailure(message : String)
        fun onUpdateSuccess()
    }
}
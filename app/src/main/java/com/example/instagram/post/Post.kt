package com.example.instagram.post

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView


interface Post {

    interface Presenter:BasePresenter{
        fun selectUri(uri: Uri)
        fun getSelectUri(): Uri?
        fun fetchPictures()
    }
    interface View: BaseView<Presenter>{
        fun showProgress(enabled : Boolean)
        fun displayFullPictures(post:List<Uri>)
        fun displayEmpytPictures()
        fun displayRequestFailure(message:String)


    }
}
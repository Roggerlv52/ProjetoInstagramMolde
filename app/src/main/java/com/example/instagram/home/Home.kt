package com.example.instagram.home

import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post

interface Home {
    interface Presenter : BasePresenter{
        fun fetchFeed()
        fun clear()
    }
    interface View : BaseView<Presenter>{
        fun showProgress(enabled : Boolean)
        fun displayRequestFailure(message:String)
        fun displayEmpytPost()
        fun displayFullPost(post:List<Post>)
    }

}
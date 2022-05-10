package com.example.instagram.profile


import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import java.util.*

interface Profile {

    interface Presenter :BasePresenter{
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPost(uuid: String?)
        fun seguirUser(uuid :String?,follow:Boolean)
        fun clear()
    }

    interface View : BaseView<Presenter>{
        fun showProgress(enabled : Boolean)
        fun displayUserProfile(user:Pair<UserAuth,Boolean?>)
        fun displayRequestFailure(message:String)
        fun displayEmpytPost()
        fun displayFullPost(post:List<Post>)
    }
}
package com.example.instagram.home.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException

interface HomeDataSource {

    fun fetchFeed(userUUID : String,callback: RequestCallback<List<Post>>)
    fun fetchSession(): UserAuth{throw UnsupportedOperationException()}
    fun putFeed(response: List<Post>?){throw UnsupportedOperationException()}

}
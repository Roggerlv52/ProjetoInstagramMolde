package com.example.instagram.profile.data

import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException
import java.net.CacheResponse

interface ProfileDataSource {
    fun fetchUserProfile(userUUID:String, callback: RequestCallback<Pair<UserAuth,Boolean?>>)
    fun fetchUserPost(userUUID : String,callback: RequestCallback<List<Post>>)
    fun followUser(userId:String,isFollw:Boolean, callback: RequestCallback<Boolean>){throw UnsupportedOperationException()}
    fun fetchSession(): UserAuth{throw UnsupportedOperationException()}
    fun putUser(response:Pair<UserAuth,Boolean?>){throw UnsupportedOperationException()}
    fun putPosts(response: List<Post>?){throw UnsupportedOperationException()}

}
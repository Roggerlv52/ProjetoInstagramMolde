package com.example.instagram.home.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class HomeLocalDataSource(private val feedcache : Cache<List<Post>>):HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {

     val posts = feedcache.get(userUUID)
        if (posts != null){
            callback.onSuccess(posts)
        }else{
            callback.onFailure("posts não existe")
        }
        callback.onComplete()
    }

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth?:throw RuntimeException("Usuario não encontrado")
    }

    override fun putFeed(response: List<Post>?) {
        feedcache.put(response)
    }
}
package com.example.instagram.home.data

import android.os.Handler
import android.os.Looper
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post

class HomeFakeRemoteDataSource: HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {

        Handler(Looper.getMainLooper()).postDelayed({
            val feeds = Database.feeds[userUUID]
                callback.onSuccess(feeds?.toList()?: emptyList())
            callback.onComplete()
        },2000)
    }


}
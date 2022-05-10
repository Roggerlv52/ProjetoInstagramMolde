package com.example.instagram.home.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.model.Post

class HomeDataSourceFactory(
    private  val feedCache : Cache<List<Post>>
) {
    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createFromFeed():HomeDataSource{
        if (feedCache.isCached()){
            return HomeLocalDataSource(feedCache)
        }
        return HomeFakeRemoteDataSource()
    }
}
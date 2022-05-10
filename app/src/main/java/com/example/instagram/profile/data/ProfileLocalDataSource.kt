package com.example.instagram.profile.data

import com.example.instagram.common.base.Cache
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class ProfileLocalDataSource(
    private val profileCache : Cache<Pair<UserAuth,Boolean?>>,
    private val postesCache : Cache<List<Post>>
):ProfileDataSource {

    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth,Boolean?>>) {
      val userAuth = profileCache.get(userUUID)
        if (userAuth != null){
            callback.onSuccess(userAuth)
        }else{
            callback.onFailure("Usuario não encontrado")
        }
        callback.onComplete()
    }

    override fun fetchUserPost(userUUID: String, callback: RequestCallback<List<Post>>) {
     val posts = postesCache.get(userUUID)
        if (posts != null){
            callback.onSuccess(posts)
        }else{
            callback.onFailure("posts não existe")
        }
        callback.onComplete()
    }

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth ?: throw RuntimeException("usurio não logado!!")
    }

    override fun putUser(response: Pair<UserAuth, Boolean?>) {
        profileCache.put(response)
    }

    override fun putPosts(response: List<Post>?) {
        postesCache.put(response)
    }
}
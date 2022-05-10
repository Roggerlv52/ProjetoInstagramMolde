package com.example.instagram.profile.data

import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.Post
import com.example.instagram.common.model.UserAuth

class ProfileFakeRemoteDataSource: ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth,Boolean?>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null){
                if (userAuth == Database.sessionAuth){
                    callback.onSuccess(Pair(userAuth,null))
                }else{
                    val follwing =  Database.followers[Database.sessionAuth!!.uuid]
                    val desUser = follwing?.firstOrNull{it == userUUID}

                    callback.onSuccess(Pair(userAuth,desUser != null))
                }

            }else{
                callback.onFailure("Usuario não encontrado")
            }

            callback.onComplete()
        }, 1000)
    }

    override fun fetchUserPost(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val posts = Database.posts[userUUID]
                callback.onSuccess(posts?.toList()?: emptyList())
            callback.onComplete()
        },1000)
    }

    override fun followUser(userId: String, isFollw: Boolean, callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({
            var seguidores = Database.followers[Database.sessionAuth!!.uuid]
            if (seguidores == null){
                seguidores = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = seguidores
            }
            if (isFollw){
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userId)
            }else{
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userId)
            }
            callback.onSuccess(true)
            callback.onComplete()
        },500)
    }

}
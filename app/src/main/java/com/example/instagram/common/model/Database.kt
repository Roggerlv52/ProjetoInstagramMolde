package com.example.instagram.common.model

import android.net.Uri
import java.io.File
import java.util.*

/*
     hashSetOf
     Adicionar um unico elemento
 */
object Database {
    val usersAuth = mutableListOf<UserAuth>()
    val posts = hashMapOf<String, MutableSet<Post>>()
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()

    var sessionAuth: UserAuth? = null

    init {
        val userA = UserAuth(UUID.randomUUID().toString(), "roger", "roggerlvr52@gmail.com", "12345678", Uri.fromFile(
                File("/storage/emulated/0/Android/media/com.example.instagram/Instagram/2022-05-07-19-52-18-018.jpg")))

        val userB = UserAuth(UUID.randomUUID().toString(), "Ana Paula", "roger@gmail.com", "87654321",Uri.fromFile(
            File("/storage/emulated/0/Android/media/com.example.instagram/Instagram/2022-05-07-19-52-18-018.jpg")))

        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = hashSetOf()
        feeds[userA.uuid] = hashSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = hashSetOf()
        feeds[userB.uuid] = hashSetOf()

        for(i in 0..30){
            val user = UserAuth(UUID.randomUUID().toString(),"User$i","user$i@gmail.com","12312312",null)
            usersAuth.add(user)
        }
                    sessionAuth = usersAuth.first ()
        followers[sessionAuth!!.uuid]?.add(usersAuth[2].uuid)
    }
}
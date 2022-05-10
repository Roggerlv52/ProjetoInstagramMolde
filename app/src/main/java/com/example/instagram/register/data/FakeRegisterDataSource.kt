package com.example.instagram.register.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.example.instagram.common.model.Database
import com.example.instagram.common.model.UserAuth
import java.util.*

class FakeRegisterDataSource : RegisterDataSource {

    override fun create(email: String, callback: RegisterCallback) {

        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

             if (userAuth == null){
                 callback.onSuccess()
             }else{
                 callback.onFailure("Usuario já cadastrado")
             }

            callback.onComplete()
        }, 2000)
    }

    override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

          if (userAuth != null){
             callback.onFailure("Usuario já existe")
          }else{
              val newUser = UserAuth(UUID.randomUUID().toString(),email,name,password,null)
              val created = Database.usersAuth.add(newUser)
              if (created){
                  Database.sessionAuth = newUser

                  Database.followers[newUser.uuid] = hashSetOf()
                  Database.posts[newUser.uuid] = hashSetOf()
                  Database.feeds[newUser.uuid] = hashSetOf()

                  callback.onSuccess()
              }else{
                  callback.onFailure("Erro interno no servidor.")
              }
          }
            callback.onComplete()
        },2000)
    }

    override fun updateUser(photo: Uri, callback: RegisterCallback) {
        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth = Database.sessionAuth

            if (userAuth == null){
                callback.onFailure("Usuario não encontrado")
            }else{
              val index = Database.usersAuth.indexOf(Database.sessionAuth)

                Database.usersAuth[index] = Database.sessionAuth!!.copy(photoUri = photo)
                Database.sessionAuth = Database.usersAuth[index]

                    callback.onSuccess()
                   // callback.onFailure("Erro interno no servidor.")
            }
            callback.onComplete()
        },1000)
    }
}


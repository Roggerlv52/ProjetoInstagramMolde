package com.example.instagram.add.data

import com.example.instagram.common.model.Database
import com.example.instagram.common.model.UserAuth

class AddLocalDataSource : AddDataSource {

    override fun fetchSession(): UserAuth {
        return Database.sessionAuth?:throw RuntimeException("Usuario não logado !")

    }
}
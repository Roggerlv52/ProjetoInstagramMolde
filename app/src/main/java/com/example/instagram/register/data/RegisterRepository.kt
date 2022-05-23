package com.example.instagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource : RegisterDataSource) {

    fun create(email : String,callback : RegisterCallback){
        dataSource.create(email,callback)
    }
    fun create(email : String, name : String, password : String,callback : RegisterCallback) {
        dataSource.create(name, email, password, callback)
    }
    fun updaterUser(photoUri : Uri, callback : RegisterCallback){
        dataSource.updateUser(photoUri,callback)
    }
}
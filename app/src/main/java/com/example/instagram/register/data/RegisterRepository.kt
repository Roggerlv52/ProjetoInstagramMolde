package com.example.instagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource : RegisterDataSource) {

    fun create(email : String,callback : RegisterCallback){
        dataSource.create(email,callback)
    }
    fun create( name : String,email : String, password : String,callback : RegisterCallback) {
        dataSource.create(email,name,password, callback)
    }
    fun updaterUser(photo : Uri, callback : RegisterCallback){
        dataSource.updateUser(photo,callback)
    }
}
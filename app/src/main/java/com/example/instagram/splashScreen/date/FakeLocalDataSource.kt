package com.example.instagram.splashScreen.date

import com.example.instagram.common.model.Database

class FakeLocalDataSource : SplashDataSource{
    override fun sesscion(callback: SplashCallback) {
        if (Database.sessionAuth != null){
            callback.onSuccess()
        }else{
            callback.onFalure()
        }
    }
}
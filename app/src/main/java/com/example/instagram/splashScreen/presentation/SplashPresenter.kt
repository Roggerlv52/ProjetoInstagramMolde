package com.example.instagram.splashScreen.presentation

import com.example.instagram.splashScreen.Splash
import com.example.instagram.splashScreen.date.SplashCallback
import com.example.instagram.splashScreen.date.SplashRepository

class SplashPresenter(
    private var view:Splash.View?,
    private val reository : SplashRepository
): Splash.Presenter {

    override fun authenticated() {
        reository.sesssion(object : SplashCallback{
            override fun onSuccess() {
             view?.goToMainScreen()
            }

            override fun onFalure() {
             view?.goToLoginScreen()
            }
        })
    }

    override fun onDestroy(){
      view = null
    }
}
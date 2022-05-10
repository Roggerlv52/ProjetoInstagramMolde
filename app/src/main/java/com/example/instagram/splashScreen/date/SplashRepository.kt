package com.example.instagram.splashScreen.date

class SplashRepository(private val dataSource: SplashDataSource) {
     fun sesssion(callback:SplashCallback){
         dataSource.sesscion(callback)
     }
}
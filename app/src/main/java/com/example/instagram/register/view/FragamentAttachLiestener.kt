package com.example.instagram.register.view
// Evento para ir para proxima tela

interface FragamentAttachLiestener {
    fun goToNameAndpasswordScreen(email : String)
    fun goToWelcomeScreen(name:String)// Welcome-pass3
    fun goToPhotoSreen()
    fun goToMainScreen()
    fun goToGalleryScreen()
    fun goToCameraSreen()

}
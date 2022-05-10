package com.example.instagram.register.presentation

import android.net.Uri
import com.example.instagram.register.RegisterPhoto
import com.example.instagram.register.data.RegisterCallback
import com.example.instagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhoto.view?,
    private val repository: RegisterRepository
): RegisterPhoto.Presenter{

    override fun updateUser(photo: Uri) {

            view?.showProgress(true)

            repository.updaterUser( photo, object : RegisterCallback{
                override fun onSuccess() {
                   view?.onUpdateSuccess()
                }
                override fun onFailure(message: String) {
                   view?.onUpdateFailure(message)
                }
                override fun onComplete() {
                  view?.showProgress(false)
                }
            })
    }
    override fun onDestroy() {
         view = null
    }
}
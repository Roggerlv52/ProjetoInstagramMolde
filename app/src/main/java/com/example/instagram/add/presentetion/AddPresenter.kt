package com.example.instagram.add.presentetion

import android.net.Uri
import com.example.instagram.add.Add
import com.example.instagram.add.data.AddRepository
import com.example.instagram.common.base.RequestCallback
import com.example.instagram.login.data.LoginRepository

class AddPresenter(
    private var view : Add.View? = null,
    private var repository: AddRepository): Add.Presenter {

    override fun createPost(uri: Uri, caption: String) {
        view?.showProgress(true)
        repository.createPost(uri,caption, object : RequestCallback<Boolean>{
            override fun onSuccess(data: Boolean) {
           if (data){
               view?.displayRequesteSuccess()
           }else{
               view?.displayRequesteFailure("internal error")
           }
            }

            override fun onFailure(message: String) {
                view?.displayRequesteFailure(message)
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
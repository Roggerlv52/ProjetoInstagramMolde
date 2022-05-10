package com.example.instagram.post.presenter
import android.net.Uri
import com.example.instagram.post.Post
import com.example.instagram.post.data.PostRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
) : Post.Presenter,CoroutineScope {

    private var uri :Uri? = null
    private val job = Job()

   override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    override fun fetchPictures() {
       view?.showProgress(true)
        launch {
       val pictures   =  repository.fetchPictures()

            withContext(Dispatchers.Main){
                if (pictures.isEmpty()){
                    view?.displayEmpytPictures()
                }else{
                    view?.displayFullPictures(pictures)
                }
                view?.showProgress(false)
            }
        }

    }

    override fun selectUri(uri: Uri) {
        this.uri = uri
    }

    override fun getSelectUri(): Uri? {
        return uri
    }

    override fun onDestroy() {
        job.cancel()
     view = null
    }

}
package com.example.instagram.post.data

import android.net.Uri

interface PostDataSource {
   suspend fun fetchPictures(): List<Uri>  //para usar em cerviço paralelo em leitura de SDCARD
}
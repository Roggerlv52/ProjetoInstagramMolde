package com.example.instagram.post.data

class PostRepository(private  val dataSource: PostDataSource) {
    /*suspend fun fetchPictures(): List<Uri>{
        return dataSource.fetchPictures()
     */
    suspend fun fetchPictures() = dataSource.fetchPictures()

}

package com.example.instagram.common.model

import android.net.Uri

data class UserAuth(
    val uuid: String,
    val name : String,
    val email: String,
    val password: String,
    val photoUri: Uri?,
    val posCount: Int = 0,
    val follwingCount: Int = 0,
    val followersCount: Int = 0)

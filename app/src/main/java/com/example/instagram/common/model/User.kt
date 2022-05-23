@file:Suppress("DEPRECATED_ANNOTATION")

package com.example.instagram.common.model

import android.net.Uri

data class User(
    val uuid: String? = null,
    val name : String? = null,
    val email: String? = null,
    val password: String,
    val photoUrl: String,
    val posCount: Int = 0,
    val follwingCount: Int = 0,
    val followersCount: Int = 0)

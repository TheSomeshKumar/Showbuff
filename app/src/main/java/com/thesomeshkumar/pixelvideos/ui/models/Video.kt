package com.thesomeshkumar.pixelvideos.ui.models

import androidx.annotation.Keep

@Keep
data class Video(
    val id: Int,
    val thumbUrl: String,
    val userName: String,
    val videoLink: String
)

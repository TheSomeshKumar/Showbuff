package com.thesomeshkumar.pixelvideos.data.response

import com.thesomeshkumar.pixelvideos.ui.models.Video

fun VideoDTO.mapToUI() = Video(
    id = id,
    thumbUrl = this.image,
    userName = this.user.name,
    videoLink = (this.videoFiles.firstOrNull()?.link ?: "")
)

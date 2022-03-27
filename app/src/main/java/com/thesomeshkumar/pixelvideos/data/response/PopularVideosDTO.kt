package com.thesomeshkumar.pixelvideos.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PopularVideosDTO(
    val page: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("total_results") val totalResults: Int,
    val url: String,
    val videos: List<VideoDTO>
)

@Keep
data class VideoDTO(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int,
    val user: User,
    @SerializedName("video_files") val videoFiles: List<VideoFile>,
    @SerializedName("video_pictures") val videoPictures: List<VideoPicture>
)

@Keep
data class User(
    val id: Int,
    val name: String,
    val url: String
)

@Keep
data class VideoFile(
    val id: Int,
    val quality: String,
    @SerializedName("file_type") val fileType: String,
    val width: Int?,
    val height: Int?,
    val link: String
)

@Keep
data class VideoPicture(
    val id: Int,
    val picture: String,
    val nr: Int
)



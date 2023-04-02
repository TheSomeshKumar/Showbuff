package com.thesomeshkumar.showbuff.ui.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TvShowUI(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview") val overview: String
)

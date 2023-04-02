package com.thesomeshkumar.showbuff.data.response // ktlint-disable filename

import com.thesomeshkumar.showbuff.data.response.MovieDTO.Movie
import com.thesomeshkumar.showbuff.data.response.TVShowDTO.TVShow
import com.thesomeshkumar.showbuff.ui.models.MovieUI
import com.thesomeshkumar.showbuff.ui.models.TvShowUI

fun TVShow.mapToUI() = TvShowUI(
    id = id,
    name = name,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

fun Movie.mapToUI() = MovieUI(
    id = id,
    name = title,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

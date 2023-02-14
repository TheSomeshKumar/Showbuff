package com.thesomeshkumar.tmdb.data.response // ktlint-disable filename

import com.thesomeshkumar.tmdb.data.response.MovieDTO.Movie
import com.thesomeshkumar.tmdb.data.response.TVShowDTO.TVShow
import com.thesomeshkumar.tmdb.ui.models.MovieUI
import com.thesomeshkumar.tmdb.ui.models.TvShowUI

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

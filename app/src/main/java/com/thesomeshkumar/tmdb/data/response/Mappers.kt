package com.thesomeshkumar.tmdb.data.response // ktlint-disable filename

import com.thesomeshkumar.tmdb.ui.models.MovieUI
import com.thesomeshkumar.tmdb.ui.models.TvShowUI

fun TVShowDTO.mapToUI() = TvShowUI(
    id = id,
    name = name,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

fun MoviesDTO.mapToUI() = MovieUI(
    id = id,
    name = title,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview.ifBlank { "N/A" }
)

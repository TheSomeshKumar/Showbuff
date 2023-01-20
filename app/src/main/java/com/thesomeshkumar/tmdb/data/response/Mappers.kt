package com.thesomeshkumar.tmdb.data.response // ktlint-disable filename

import com.thesomeshkumar.tmdb.ui.models.Movie
import com.thesomeshkumar.tmdb.ui.models.TvShow

fun TVShowDTO.mapToUI() = TvShow(
    id = id,
    name = name,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview
)

fun MoviesDTO.mapToUI() = Movie(
    id = id,
    name = title,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview
)

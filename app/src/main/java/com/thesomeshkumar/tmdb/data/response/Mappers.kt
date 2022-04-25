package com.thesomeshkumar.tmdb.data.response

import com.thesomeshkumar.tmdb.ui.models.TvShow

fun TVShowDTO.mapToUI() = TvShow(
    id = id,
    name = name,
    posterPath = posterPath ?: "N/A",
    backdropPath = backdropPath ?: "N/A",
    overview = overview
)

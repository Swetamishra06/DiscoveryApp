package com.example.discoveryapp

data class Release(
    val id: Int,
    val imdb_id: String,
    val is_original: Int,
    val poster_url: String,
    val season_number: Int,
    val source_id: Int,
    val source_name: String,
    val source_release_date: String,
    val title: String,
    val tmdb_id: Int,
    val tmdb_type: String,
    val type: String
)
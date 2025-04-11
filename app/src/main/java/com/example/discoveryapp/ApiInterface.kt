package com.example.discoveryapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("releases")
    fun getMoviesAndShows(
        @Query("apiKey") apiKey: String,

    ): Call<MoviesAndShows>
}

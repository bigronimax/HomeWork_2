package com.example.homework_2

import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("/v1/gifs/trending")

    fun getGifs(@Query("limit") limit: Int,
                @Query("api_key") key: String,
                @Query("offset") start: Int)
            : retrofit2.Call<DataResult>
    @GET("/v1/gifs/search")
    fun searchGifs(@Query("q") search: String,
                   @Query("limit") limit: Int,
                   @Query("api_key") key: String,
                   @Query("offset") start: Int)
            : retrofit2.Call<DataResult>

}
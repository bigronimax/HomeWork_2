package com.example.homework_2

import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    //@GET("gifs/trending?api_key=$api")

    @GET("/v1/gifs/trending") //для популярных

    fun getGifs(@Query("limit") limit: Int,
                @Query("api_key") key: String)//Ключ
            : retrofit2.Call<DataResult>
    @GET("/v1/gifs/search") //для поиска
    fun searchGifs(@Query("q") search: String,
                   @Query("limit") limit: Int,
                   @Query("api_key") key: String): retrofit2.Call<DataResult>
}
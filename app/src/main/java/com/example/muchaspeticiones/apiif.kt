package com.example.muchaspeticiones

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface apiif {

    @GET("{comicId}/info.0.json")
    fun getRandomComic(@Path("comicId") comicId: Int): Call<xkcdGETItem>

    @GET("info.0.json")
    fun getLatestComic() : Call<xkcdGETItem>


}
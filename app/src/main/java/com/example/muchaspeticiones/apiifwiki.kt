package com.example.muchaspeticiones

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface apiifwiki {
    @GET("{month}/{day}")
    fun getDeaths(@Path("month") month : String,
                  @Path("day")   day   : String): Call<wikiGET>
}
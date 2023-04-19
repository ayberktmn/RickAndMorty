package com.ayberk.rickandmortyy.di.retrofit


import com.ayberk.rickandmorty20.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {


    @GET("character/")  // page= hangi sayı verilirse o sayfa açılır
    fun getCharacter(@Query("page")query: String): retrofit2.Call<Character>


    @GET("location/")
    fun getLocation(@Query("page") query: String): retrofit2.Call<LocationX>
}
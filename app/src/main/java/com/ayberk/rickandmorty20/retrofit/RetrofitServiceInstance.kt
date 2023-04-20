package com.ayberk.rickandmorty20.models.AnaCharacter


import com.ayberk.rickandmorty20.models.*


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {


    @GET("character/{id}")  // page= hangi sayı verilirse o sayfa açılır
    fun getCharacter(@Path("id")page: String): retrofit2.Call<SingilurCharacter>


    @GET("location/")
    fun getLocation(@Query("page") query: String): retrofit2.Call<LocationX>
}
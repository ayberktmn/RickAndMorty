package com.ayberk.rickandmorty20.retrofit

import androidx.lifecycle.MutableLiveData
import com.ayberk.rickandmorty20.models.AnaCharacter.RetrofitServiceInstance
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacter
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class RetrofitRepository@Inject constructor(private val retrofitService: RetrofitServiceInstance) {


    fun getCharacter(page:String,liveData: MutableLiveData<SingilurCharacter>) {
        retrofitService.getCharacter(page).enqueue(object : retrofit2.Callback<SingilurCharacter> {
            override fun onResponse(call: Call<SingilurCharacter>, response: Response<SingilurCharacter>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SingilurCharacter>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }


    fun getLocation(page: String, liveData: MutableLiveData<com.ayberk.rickandmorty20.models.LocationX>) {
        retrofitService.getLocation(page).enqueue(object : retrofit2.Callback<com.ayberk.rickandmorty20.models.LocationX> {
            override fun onResponse(call: Call<com.ayberk.rickandmorty20.models.LocationX>, response: Response<com.ayberk.rickandmorty20.models.LocationX>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<com.ayberk.rickandmorty20.models.LocationX>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }
}

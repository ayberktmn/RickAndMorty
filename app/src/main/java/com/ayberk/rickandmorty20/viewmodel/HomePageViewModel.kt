package com.ayberk.rickandmorty20.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacter
import com.ayberk.rickandmorty20.retrofit.RetrofitRepository



import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repo: RetrofitRepository): ViewModel() {

    var charactersList : MutableLiveData<SingilurCharacter>
    var locationList : MutableLiveData<com.ayberk.rickandmorty20.models.LocationX>


    init {
      charactersList = MutableLiveData()
        locationList = MutableLiveData()
    }

    fun getObserverLiveData() : MutableLiveData<SingilurCharacter>{
        return charactersList
    }

    fun loadCharacterData(page: String){
        repo.getCharacter(page,charactersList)
    }
    fun getLocationData() : MutableLiveData<com.ayberk.rickandmorty20.models.LocationX> {
        return locationList
    }

    fun loadLocationData(page:String){
        repo.getLocation(page,locationList)
    }

}
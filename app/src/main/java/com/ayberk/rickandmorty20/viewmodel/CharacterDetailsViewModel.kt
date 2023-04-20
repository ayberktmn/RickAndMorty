package com.ayberk.rickandmorty20.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.rickandmorty20.models.AnaCharacter.SingilurCharacter
import com.ayberk.rickandmorty20.retrofit.RetrofitRepository



import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val repo: RetrofitRepository): ViewModel() {

    var charactersList : MutableLiveData<SingilurCharacter>

    init {
      charactersList = MutableLiveData()

    }

    fun getObserverLiveData() : MutableLiveData<SingilurCharacter>{
        return charactersList
    }
    fun loadCharacterData(page: String){
        repo.getCharacter(page,charactersList)
    }

}
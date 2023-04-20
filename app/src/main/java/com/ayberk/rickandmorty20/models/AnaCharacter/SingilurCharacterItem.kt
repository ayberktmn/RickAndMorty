package com.ayberk.rickandmorty20.models.AnaCharacter

import com.ayberk.rickandmorty20.models.Location
import com.ayberk.rickandmorty20.models.Origin

data class SingilurCharacterItem(
    val created: String,
    val episode: ArrayList<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
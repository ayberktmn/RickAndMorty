package com.ayberk.rickandmorty20.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsArguments(
    val name: String,
    val status: String,
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val episode: List<String>,
    val origin: String,
    val location: String,
): Parcelable
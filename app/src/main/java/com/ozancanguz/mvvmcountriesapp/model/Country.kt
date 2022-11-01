package com.ozancanguz.mvvmcountriesapp.model


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("capital")
    val countryCapital: String,
    @SerializedName("currency")
    val countryCurrency: String,
    @SerializedName("flag")
    val imageUrl: String,
    @SerializedName("language")
    val countryLanguage: String,
    @SerializedName("name")
    val countryName: String,
    @SerializedName("region")
    val countryRegion: String
)
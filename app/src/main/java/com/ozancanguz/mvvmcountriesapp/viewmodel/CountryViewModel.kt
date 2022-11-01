package com.ozancanguz.mvvmcountriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozancanguz.mvvmcountriesapp.model.Country

class CountryViewModel: ViewModel() {

    var countryLiveData=MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country=Country("ankara","tl","ozan"

        ,"turkish","turkey","asia")

        countryLiveData.value=country

    }






}
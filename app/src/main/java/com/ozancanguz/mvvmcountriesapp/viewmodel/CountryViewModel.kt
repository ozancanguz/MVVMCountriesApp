package com.ozancanguz.mvvmcountriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozancanguz.mvvmcountriesapp.model.Country
import com.ozancanguz.mvvmcountriesapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application:Application): BaseViewModel(application) {

    var countryLiveData=MutableLiveData<Country>()

    fun getDataFromRoom(uuid:Int){
        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country

        }


    }






}
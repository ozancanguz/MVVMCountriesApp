package com.ozancanguz.mvvmcountriesapp.service

import com.ozancanguz.mvvmcountriesapp.model.Country
import io.reactivex.Single
import retrofit2.http.GET

// for retrofit part 1
interface CountryApi {


  //  https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>

}
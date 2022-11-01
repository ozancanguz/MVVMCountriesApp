package com.ozancanguz.mvvmcountriesapp.service

import com.ozancanguz.mvvmcountriesapp.model.Country
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {


      private val BASE_URL="https://raw.githubusercontent.com"

       private val api=Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .build()
           .create(CountryApi::class.java)

    fun getData():Single<List<Country>>{
        return api.getCountries()
    }



}
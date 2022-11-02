package com.ozancanguz.mvvmcountriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozancanguz.mvvmcountriesapp.model.Country
import com.ozancanguz.mvvmcountriesapp.service.CountryApi
import com.ozancanguz.mvvmcountriesapp.service.CountryApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel: ViewModel() {

    // for retrofit part 3
    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()


    var countries=MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromRemote()
    }


     private fun getDataFromRemote(){

         countryLoading.value=true

         disposable.add(
          countryApiService.getData()
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                  override fun onSuccess(value: List<Country>?) {
                      if (value != null) {
                          storeInSQLite(value)
                      }

                   }

                  override fun onError(e: Throwable?) {
                      countryError.value=true

                  }

              }
         )

         )
     }


    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>){


    }






}
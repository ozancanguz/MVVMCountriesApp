package com.ozancanguz.mvvmcountriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozancanguz.mvvmcountriesapp.Util.CustomSharedPreferences
import com.ozancanguz.mvvmcountriesapp.model.Country
import com.ozancanguz.mvvmcountriesapp.service.CountryApi
import com.ozancanguz.mvvmcountriesapp.service.CountryApiService
import com.ozancanguz.mvvmcountriesapp.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application) {

    // for retrofit part 3
    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()

    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    var countries=MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromRemote()
        }

    }
    fun refreshFromAPI() {
        getDataFromRemote()
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
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
                          Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
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
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // -> list -> individual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)

        }


    }






}
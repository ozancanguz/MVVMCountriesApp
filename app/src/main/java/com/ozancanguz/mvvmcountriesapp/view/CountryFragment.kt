package com.ozancanguz.mvvmcountriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ozancanguz.mvvmcountriesapp.R
import com.ozancanguz.mvvmcountriesapp.downloadFromUrl
import com.ozancanguz.mvvmcountriesapp.placeholderProgressBar
import com.ozancanguz.mvvmcountriesapp.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {

            private var countryUuid=0

    //1
    private lateinit var viewmodel:CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUuid
        }
        //2
        viewmodel=ViewModelProvider(this).get(CountryViewModel::class.java)
        viewmodel.getDataFromRoom(countryUuid)



         observeLiveData()


    }
    private fun observeLiveData(){
        viewmodel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country.let {
                countryName.text = country.countryName
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                countryRegion.text = country.countryRegion
                context?.let {
                    countryImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }


            }

        })

    }


}
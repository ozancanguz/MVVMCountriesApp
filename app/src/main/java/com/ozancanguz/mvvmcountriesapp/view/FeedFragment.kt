package com.ozancanguz.mvvmcountriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozancanguz.mvvmcountriesapp.R
import com.ozancanguz.mvvmcountriesapp.adapter.CountryAdapter
import com.ozancanguz.mvvmcountriesapp.viewmodel.FeedViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    //1
    private lateinit var viewModel: FeedViewModel
    private  var countryAdapter=CountryAdapter(arrayListOf())





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)

    }

    //2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this).get(FeedViewModel::class.java)

        viewModel.refreshData()
        countryList.layoutManager=LinearLayoutManager(context)
        countryList.adapter=countryAdapter

        observeLiveData()


    }

    //3
    fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries.let {
                countryList.visibility=View.VISIBLE
                countryAdapter.updateCountryList(countries)

            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error.let {
               if(it){
                   countryError.visibility=View.VISIBLE
               }
                else{
                    countryError.visibility=View.GONE
               }
            }

        })


        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }

}
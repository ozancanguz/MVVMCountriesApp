package com.ozancanguz.mvvmcountriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.mvvmcountriesapp.R
import com.ozancanguz.mvvmcountriesapp.downloadFromUrl
import com.ozancanguz.mvvmcountriesapp.model.Country
import com.ozancanguz.mvvmcountriesapp.placeholderProgressBar
import com.ozancanguz.mvvmcountriesapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.fragment_country.view.*
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(private val countryList:ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


    class CountryViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.itemView.name.text=countryList[position].countryName
        holder.itemView.region.text=countryList[position].countryRegion

        // to navigate countryviewmodel
        holder.itemView.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            action.countryUuid=countryList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.downloadFromUrl(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context))


    }

    override fun getItemCount(): Int {
        return countryList.size
    }


}
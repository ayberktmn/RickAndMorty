package com.ayberk.rickandmorty20.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.rickandmorty20.HomeFragment
import com.ayberk.rickandmorty20.HomeFragmentDirections
import com.ayberk.rickandmorty20.R
import com.bumptech.glide.Glide

class LocationAdapter: RecyclerView.Adapter<LocationAdapter.MyCustomHolder>(){

    var liveData: List<com.ayberk.rickandmorty20.models.ResultX>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item,parent,false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!!.get(position))

        holder.btnLocaiton.setOnClickListener {
            val action =  HomeFragmentDirections.actionHomeFragmentSelf(position)
            holder.view.findNavController().navigate(action)
           // println(position)
        }

    }

    override fun getItemCount(): Int {
        if(liveData == null){
            return 0
        }
        else{
            return liveData!!.size
        }
    }

    class MyCustomHolder(val view: View):
        RecyclerView.ViewHolder(view){

        val btnLocaiton = view.findViewById<Button>(R.id.locaitonbtn)

        fun bind(data:com.ayberk.rickandmorty20.models.ResultX) {

            btnLocaiton.setText(data.name)
        }
    }
    fun setLists(liveData: List<com.ayberk.rickandmorty20.models.ResultX>){
        this.liveData = liveData
        notifyDataSetChanged()
    }
}
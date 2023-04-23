package com.ayberk.rickandmorty20.adapter

import android.graphics.Color
import android.renderscript.ScriptIntrinsicYuvToRGB
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.rickandmorty20.HomeFragmentDirections
import com.ayberk.rickandmorty20.R
import java.util.*

class LocationAdapter: RecyclerView.Adapter<LocationAdapter.MyCustomHolder>(){

    private var selectedPosition:Int = -1
    var liveData: List<com.ayberk.rickandmorty20.models.ResultX>? = null
    // Buton tıklanıp tıklanmadığını takip etmek için bir değişken

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_item,parent,false)

        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        val btnLocation = holder.view.findViewById<Button>(R.id.locaitonbtn)

        holder.bind(liveData!!.get(position))

        if (position == selectedPosition) {
            btnLocation.setBackgroundColor(Color.GRAY)
        } else {
            btnLocation.setBackgroundColor(Color.BLUE)
        }
        btnLocation.setOnClickListener {
            selectedPosition = holder.getAdapterPosition()
            notifyDataSetChanged()
            val action = HomeFragmentDirections.actionHomeFragmentSelf(position)
            println(position)
            holder.view.findNavController().navigate(action)
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
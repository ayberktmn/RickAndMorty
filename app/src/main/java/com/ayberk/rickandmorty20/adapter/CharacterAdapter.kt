package com.ayberk.rickandmorty20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.rickandmorty20.HomeFragment
import com.ayberk.rickandmorty20.HomeFragmentDirections
import com.ayberk.rickandmorty20.R

import com.bumptech.glide.Glide

class CharacterAdapter: RecyclerView.Adapter<CharacterAdapter.MyCustomHolder>(){

    var liveData: List<com.ayberk.rickandmorty20.models.Result>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characters_item,parent,false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        holder.bind(liveData!!.get(position))
        holder.chaimage?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(position)
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

        val txttitlepop = view.findViewById<TextView>(R.id.title)
        val chaimage = view.findViewById<ImageView>(R.id.chaimage)
        val seximage = view.findViewById<ImageView>(R.id.seximage)


        //  val txtPopGenre = view.findViewById<TextView>(R.id.txtPopGenre)

        fun bind(data:com.ayberk.rickandmorty20.models.Result) {
            txttitlepop.text = data.name

            var into = Glide.with(chaimage)
                .load(data.image)
                .into(chaimage)

            if (data.gender=="Male") {
                var intomale = Glide.with(seximage)
                    .load(R.drawable.male)
                    .into(seximage)
            }
            if (data.gender=="Female"){
                var intofemale = Glide.with(seximage)
                    .load(R.drawable.female)
                    .into(seximage)
            }
            if (data.gender=="unknown"){
                var intounknown = Glide.with(seximage)
                    .load(R.drawable.question)
                    .into(seximage)
            }
        }
    }
    fun setLists(liveData: List<com.ayberk.rickandmorty20.models.Result>?) {
        if (liveData != null) {
            this.liveData = liveData
            notifyDataSetChanged()
        }
    }
}
package com.example.pokeinfo

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pokeinfo.models.Pokemon
import kotlinx.android.synthetic.main.list_element_pokemon.view.*
import com.example.pokeinfo.utilities.AppConstants

class PokemonAdapter(val items: List<Pokemon>, var context: Context) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_element_pokemon, parent, false)

        view.setOnClickListener {
            Log.d("siEntra","entro al click listener")
            val mIntent = Intent(context, PokemonDataActivity::class.java)
            mIntent.putExtra(AppConstants.POKEMON_ID_KEY,view.tv_pokemon_id.text)
            mIntent.putExtra(AppConstants.POKEMON_NAME_KEY,view.tv_pokemon_name.text)
            view.context.startActivity(mIntent)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*init{
            view.setOnClickListener {
                val mIntent = Intent(context, PokemonDataActivity::class.java)
                //mIntent.putExtra(AppConstants.POKEMON_ID_KEY,view.tv_pokemon_id.text)
                mIntent.putExtra(AppConstants.POKEMON_NAME_KEY,view.tv_pokemon_name.text)
                view.context.startActivity(mIntent)
            }

        }*/
        fun bind(item: Pokemon) = with(itemView) {
            tv_pokemon_id.text = item.id.toString()
            tv_pokemon_name.text = item.name
            //tv_pokemon_type.text = item.type
        }
    }

}
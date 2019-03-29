package com.example.pokeinfo

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.pokeinfo.models.Pokemon
import com.example.pokeinfo.utilities.AppConstants
import com.example.pokeinfo.utilities.NetworkUtils
import kotlinx.android.synthetic.main.activity_pokemon_data.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PokemonDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_data)
        var intent: Intent = getIntent()
        FetchPokemonDataTask().execute()
    }

    private inner class FetchPokemonDataTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg pokemonNumbers: String?): String {
            Log.d("Id que trae el intent", intent.getStringExtra(AppConstants.POKEMON_ID_KEY))
            val pokeAPI = NetworkUtils.buildUrl(null, (intent.getStringExtra(AppConstants.POKEMON_ID_KEY)).toString())

            return try {
                NetworkUtils.getResponseFromHttpUrl(pokeAPI)!!
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String?) {
            if (pokemonInfo != null || pokemonInfo != "") {
                Glide.with(this@PokemonDataActivity).load(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + intent.getStringExtra(
                        AppConstants.POKEMON_ID_KEY
                    ) + ".png"
                ).into(img_ind_pokemon)
                var jsonPokemon = JSONObject(pokemonInfo)
                tv_ind_pokemon_id.text = intent.getStringExtra(AppConstants.POKEMON_ID_KEY).toString()
                tv_ind_pokemon_name.text = jsonPokemon.getString("name").capitalize()
                tv_ind_pokemon_weight.text = jsonPokemon.getString("weight")
                tv_ind_pokemon_height.text = jsonPokemon.getString("height")
                tv_ind_pokemon_xp.text = jsonPokemon.getString("base_experience")
                var typePokemon = (JSONArray(jsonPokemon.getString("types"))[0])
                // var jsonTypePokemon =
                Log.d("tipoPokemon", typePokemon.toString())


                //mResultText.setText(pokemonInfo)
            }
            //initRecycler()
        }
    }
}

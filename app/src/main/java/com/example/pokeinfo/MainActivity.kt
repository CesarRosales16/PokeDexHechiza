package com.example.pokeinfo

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.pokeinfo.models.Pokemon
import com.example.pokeinfo.utilities.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    var pokemonList: ArrayList<Pokemon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FetchPokemonTask().execute()

    }


    fun initRecycler() {

        viewManager = LinearLayoutManager(this)
        viewAdapter = PokemonAdapter(pokemonList, this)

        rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg pokemonNumbers: String?): String {

            val pokeAPI = NetworkUtils.buildUrl("pokemon?offset=0&limit=200",null)

            return try {
                NetworkUtils.getResponseFromHttpUrl(pokeAPI)!!
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String?) {
            if (pokemonInfo != null || pokemonInfo != "") {
                var jsonPokemons = JSONObject(pokemonInfo);
                var arrayPokemons = jsonPokemons.getJSONArray("results")
                for (i in 0 until arrayPokemons.length()) {
                    var pokemon = Pokemon((i + 1), arrayPokemons.getJSONObject(i).getString("name").capitalize())
                    pokemonList.add(pokemon)
                }
            }
            initRecycler()
        }
    }
}

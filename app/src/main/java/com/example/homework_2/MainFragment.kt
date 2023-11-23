package com.example.homework_2

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.giphy.com/v1/"
const val API = "F1lp9CrCgfCxEOMQN3Fdcw5sROer8RQB"
const val LIMIT = 20

class MainFragment : Fragment() {

    private lateinit var adapter : GifsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var gifs: MutableList<DataObject>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rcView)

        gifs = mutableListOf<DataObject>()
        adapter = GifsAdapter(this.requireContext(), gifs)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        else
            recyclerView.layoutManager = GridLayoutManager(this.context, 4)


        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroService = retrofit.create(DataService::class.java)
        retroService.getGifs(LIMIT, API).enqueue(object : Callback<DataResult?>{
            override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>) {
                val body = response.body()

                gifs.addAll(body!!.res)
                adapter.notifyDataSetChanged()
            }



            override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                Toast.makeText(context, "network failure :(", Toast.LENGTH_SHORT).show();
            }
        })

        fun GetSearch(query: String){
            retroService.searchGifs(query, LIMIT, API).enqueue(object : Callback<DataResult?>
            {
                override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>)
                {
                    gifs.clear();//очищаем список
                    val body = response.body()

                    gifs.addAll(body!!.res)//загружаем новые данные
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                    Toast.makeText(context, "network failure :(", Toast.LENGTH_SHORT).show();
                }
            })}

        searchView = view.findViewById(R.id.sv)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean
            {
                GetSearch(query) //вызываем функцию поиска, передаем "искомое" слово
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })




    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // outState.put("gifs", gifs) ???????????
    }

}
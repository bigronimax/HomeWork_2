package com.example.homework_2

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2.databinding.ElemItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.giphy.com/v1/"
const val API = "F1lp9CrCgfCxEOMQN3Fdcw5sROer8RQB"
const val LIMIT = 10

class MainFragment : Fragment(), GifsAdapter.IListener {

    private lateinit var adapter : GifsAdapter
    private lateinit var holder: GifsAdapter.GifsHolder
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var gifs: MutableList<DataObject>
    private lateinit var load: ProgressBar
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var btn: Button




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        load = view.findViewById(R.id.pg)
        load.visibility = View.GONE;
        btn = view.findViewById(R.id.btn)
        recyclerView = view.findViewById(R.id.rcView)

        gifs = mutableListOf<DataObject>()
        adapter = GifsAdapter(this.requireContext(), gifs, this)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)




        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager = GridLayoutManager(this.context, 2)
        else
            gridLayoutManager = GridLayoutManager(this.context, 4)

        recyclerView.layoutManager = gridLayoutManager

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retroService = retrofit.create(DataService::class.java)

        var start = 0
        var search = false
        var req = ""

        fun getTrend() {

            load.visibility = View.VISIBLE
            retroService.getGifs(LIMIT, API, start).enqueue(object : Callback<DataResult?> {
                override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>) {
                    val body = response.body()

                    gifs.addAll(body!!.res)
                    adapter.notifyDataSetChanged()
                    load.visibility = View.GONE

                }


                override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                    Toast.makeText(context, "network failure :(", Toast.LENGTH_SHORT).show();
                }
            })

        }

        fun getSearch(query: String){
            load.visibility = View.VISIBLE;
            retroService.searchGifs(query, LIMIT, API, start).enqueue(object : Callback<DataResult?>
            {
                override fun onResponse(call: Call<DataResult?>, response: Response<DataResult?>)
                {
                    val body = response.body()

                    gifs.addAll(body!!.res)
                    adapter.notifyDataSetChanged()
                    load.visibility = View.GONE
                }

                override fun onFailure(call: Call<DataResult?>, t: Throwable) {
                    Toast.makeText(context, "network failure :(", Toast.LENGTH_SHORT).show();
                }
            })
        }

        searchView = view.findViewById(R.id.sv)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean
            {
                if (query != "trend") {
                    gifs.clear()
                    start = 0
                    getSearch(query)
                    start += LIMIT
                    search = true
                    req = query
                    return false
                }
                else {
                    gifs.clear()
                    start = 0
                    getTrend()
                    start += LIMIT
                    search = false
                    return false
                }

            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        getTrend()
        start += LIMIT


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {


                val visibleItemCount = gridLayoutManager.childCount
                val pastVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition()
                var total: Int
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                     total = adapter.itemCount
                else
                    total = adapter.itemCount


                btn.setOnClickListener() {
                    if ((visibleItemCount + pastVisibleItem) >= total && !search) {
                        getTrend()
                        start += LIMIT
                    }
                    else if ((visibleItemCount + pastVisibleItem) >= total && search) {
                        getSearch(req)
                        start += LIMIT
                    }
                    else {
                        Toast.makeText(context, "You haven't checked all gifs!", Toast.LENGTH_SHORT).show();
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })



    }

    override fun open(item: DataObject) {
        val fragment = InFragment.newInstance(item)

        parentFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        
    }

}
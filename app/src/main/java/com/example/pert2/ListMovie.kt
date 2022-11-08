package com.example.pert2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pert2.`interface`.TmdbEndpoints
import com.example.pert2.adapter.MainAdapter
import com.example.pert2.models.PopulerMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import services.ServiceBuilder


class ListMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie)

        val rvUser: RecyclerView = findViewById(R.id.recyclerview)
        getMovieNew(rvUser)
    }

    fun getMovieNew(recyclerView: RecyclerView){
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<PopulerMovies>{
            override fun onResponse(call: Call<PopulerMovies>, response: Response<PopulerMovies>) {
                if (response.isSuccessful){

                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListMovie)
                        adapter = MainAdapter(response.body()!!.results)
                    }
                    Log.e("resp",response.body().toString())
                }
            }
            override fun onFailure(call: Call<PopulerMovies>, t: Throwable) {
                Toast.makeText(this@ListMovie, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


}
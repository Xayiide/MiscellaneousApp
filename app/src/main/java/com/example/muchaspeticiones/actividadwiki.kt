package com.example.muchaspeticiones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_actividadwiki.*
import kotlinx.android.synthetic.main.activity_actividadxkcd.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.text.StringBuilder

const val BASEWIKI = "https://es.wikipedia.org/api/rest_v1/feed/onthisday/deaths/"

class actividadwiki : AppCompatActivity() {

    var deaths = arrayListOf<wikiGETItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividadwiki)

        getwikideaths()
        initRecycler()

    }

    private fun getwikideaths() {
        val retrofitbuilder = retrofit2.Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEWIKI)
            .build()
            .create(apiifwiki::class.java)

        val month = SimpleDateFormat("MM").format(Date())
        val day   = SimpleDateFormat("dd").format(Date())
        Log.i("Month", month)
        Log.i("Day", day)

        val data: retrofit2.Call<wikiGET> = retrofitbuilder.getDeaths("02", "08")

        data.enqueue(object : Callback<wikiGET?> {
            override fun onResponse(call: retrofit2.Call<wikiGET?>,
                                    response: Response<wikiGET?>) {
                val responseBody = response.body()!!

                deaths = responseBody.deaths

                for (death in deaths) {
                    val bb = StringBuilder()
                    try {
                        val name: String? = death.pages[0].displaytitle
                        val desc: String? = death.pages[0].description
                        if (name == null || desc == null) {
                            continue
                        }
                        bb.append(name)
                        bb.append(", ")
                        bb.append(desc)
                        bb.append("\n")
                        print(bb)
                    } catch (e: Exception) {
                        continue
                    }
                }

            } /* onResponse */

            override fun onFailure(call: retrofit2.Call<wikiGET?>?, t: Throwable?) {
                val msg: String? = t?.message
                if (msg != null) {
                    Log.v("ActividadWIKI", msg)
                }
                Toast.makeText(this@actividadwiki,
                                R.string.triste,
                                Toast.LENGTH_SHORT).show()
            } /* onFailure */
        }) /* data.enqueue */

        Log.v("[getwikideaths]", "Saliendo")

    } /* getwikideaths()   */


    private fun initRecycler() {
        Log.i("[initRecycler]", "Iniciando recycler")
        idrviewwiki.layoutManager = LinearLayoutManager(this)
        val adapter = wikiadapter(deaths)
        idrviewwiki.adapter = adapter
    }



} /* class actividadwiki   */
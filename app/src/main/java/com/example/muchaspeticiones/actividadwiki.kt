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

    var listamoridos = listOf<moridos>()

    var deaths = arrayListOf<wikiGETItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividadwiki)

        // initRecycler()
        getwikideaths()
        Log.i("AAAA", "AAAAAAAAAAAAAAAAAA")
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

        val data: retrofit2.Call<wikiGET> = retrofitbuilder.getDeaths(month, day)

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
                        val img : String? = death.pages[0].thumbnail.source
                        val anio: String = death.year.toString()
                        if (name == null || desc == null || img == null) {
                            continue
                        }
                        // bb.append(name)
                        // bb.append(", ")
                        // bb.append(desc)
                        // bb.append("\n")
                        // print(bb)

                        listamoridos = listamoridos + moridos(name, desc, img, anio)
                        Log.i("[Añadiendo a moridos: ]", name + " " + desc + " " + img + "\n")

                    } catch (e: Exception) {
                        continue
                    }
                }
                showmoridos()
                initRecycler()
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
        val adapter = wikiadapter(listamoridos)
        idrviewwiki.adapter = adapter
    } /* initRecycler() */

    private fun showmoridos() {
        for (morido in listamoridos) {
            println("Nombre: " + morido.nombre)
            println("Descri: " + morido.desc)
            println("imagen: " + morido.img + "\n")
        }
    }


} /* class actividadwiki   */
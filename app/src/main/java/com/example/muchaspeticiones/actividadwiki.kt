package com.example.muchaspeticiones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_actividadxkcd.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.StringBuilder

const val BASEWIKI = "https://es.wikipedia.org/api/rest_v1/feed/onthisday/deaths/"

class actividadwiki : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividadwiki)

        getwikideaths()

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

                val deaths = responseBody.deaths


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
    } /* getwikibirths()   */
} /* class actividadwiki   */
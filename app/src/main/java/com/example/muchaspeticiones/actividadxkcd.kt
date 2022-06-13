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
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

const val BASEXKCD = "https://xkcd.com/"

class actividadxkcd : AppCompatActivity() {

    var latest    : Int     = 0
    var firsttime : Boolean = true
    var imglink   : String  = ""
    var title     : String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividadxkcd)

        Log.i("ActividadXKCD", "AAAAA")
        getrandomxkcd()
        Log.i("ActividadXKCD", "BBBBB")
    }

    private fun getrandomxkcd() {
        val retrofitbuilder = retrofit2.Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEXKCD)
            .build()
            .create(apiif::class.java)

        Log.i("ActividadXKCD", "CCCCC")

        val data: retrofit2.Call<xkcdGETItem> = if (firsttime) {
            Log.i("ActividadXKCD", "AASDASDADASD")
            retrofitbuilder.getLatestComic()
        } else {
            Log.i("ActividadXKCD", "Esto no debería salir")
            retrofitbuilder.getRandomComic((0..latest).random()) // retrofitbuilder.getRandomComic()
        }

        Log.i("ActividadXKCD", "DDDDD")

        data.enqueue(object: retrofit2.Callback<xkcdGETItem?> {
            override fun onResponse(call: retrofit2.Call<xkcdGETItem?>,
                                    response: Response<xkcdGETItem?>) {
                val responseBody = response.body()!!

                Log.i("body.title:", responseBody.title)
                Log.i("body.img:",   responseBody.img)
                Log.i("body.link:",  responseBody.link)
                Log.i("body.num:",   responseBody.num.toString())

                if (firsttime) {
                    latest    = responseBody.num
                    firsttime = false
                }

                var titlesb = StringBuilder()

                imglink = responseBody.img
                title   = responseBody.title
                var yea = responseBody.year
                var mon = responseBody.month
                var day = responseBody.day

                titlesb.append("$title - $yea/$mon/$day")

                val xkcdimg  = findViewById<ImageView>(R.id.idimgxkcd)
                val xkcdtit  = findViewById<TextView>(R.id.idtxtxkcd)
                val xkcdotro = findViewById<Button>(R.id.idbotonotroxkcd)

                xkcdotro.setOnClickListener {
                    getrandomxkcd()
                }

                if (imglink != "") {
                    Picasso.get().load(imglink).into(xkcdimg)
                }

                xkcdtit.text = titlesb


            } /* onResponse */

            override fun onFailure(call: retrofit2.Call<xkcdGETItem?>?, t: Throwable?) {
                val msg: String? = t?.message
                if (msg != null) {
                    Log.v("ActividadXKCD", msg)
                }
                Toast.makeText(this@actividadxkcd,
                                R.string.triste,
                                Toast.LENGTH_SHORT).show()
            } /* onFailure */
        }) /* data.enqueue */

    } /* getrandomxkcd */
}
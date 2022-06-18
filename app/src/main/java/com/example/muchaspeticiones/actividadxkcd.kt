package com.example.muchaspeticiones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import com.example.muchaspeticiones.IOHelper as IOHelper

const val BASEXKCD = "https://xkcd.com/"

class actividadxkcd : AppCompatActivity() {

    val ioh = IOHelper()
    var fncounter: Int = 0;

    var latest    : Int     = 0
    var firsttime : Boolean = true
    var imglink   : String  = ""
    var title     : String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividadxkcd)

        getrandomxkcd()
    } /* onCreate() */

    private fun getrandomxkcd() {
        val retrofitbuilder = retrofit2.Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEXKCD)
            .build()
            .create(apiifxkcd::class.java)

        val data: retrofit2.Call<xkcdGETItem> = if (firsttime) {
            retrofitbuilder.getLatestComic()
        } else {
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
                val xkcdsave = findViewById<Button>(R.id.idbotonguardarxkcd)

                xkcdotro.setOnClickListener {
                    getrandomxkcd()
                }

                xkcdsave.setOnClickListener {
                    guardarxkcd(responseBody)
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
    } /* getrandomxkcd     */

    private fun guardarxkcd(comic: xkcdGETItem) {
        val path: String = filesDir.path.toString()


        val secs    : String = (System.currentTimeMillis() * 0.001).toInt().toString()
        val filename: String =  path + "/" + secs + "_" + fncounter.toString() + ".log"
        val text    : String = "num=" + comic.num.toString() + "\n" +
                               "title=" + comic.title
        Log.i("AAAAA", filename)
        ioh.writefile(filename, text)
    } /* guardarxkcd() */
} /* class actividadxkcd   */

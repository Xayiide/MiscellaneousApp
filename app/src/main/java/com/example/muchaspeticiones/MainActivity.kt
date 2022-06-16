package com.example.muchaspeticiones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bxkcd = findViewById<Button>(R.id.idbotonxkcd)
        val bwiki = findViewById<Button>(R.id.idbotonwiki)

        bxkcd.setOnClickListener {
            val intent = Intent(this, actividadxkcd::class.java)
            startActivity(intent)
        }

        bwiki.setOnClickListener {
            val intent = Intent(this, actividadwiki::class.java)
            startActivity(intent)
        }

    }



}
package com.example.camscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var bundle :Bundle ?=intent.extras
        var message = bundle!!.getString("result")

        button = findViewById(R.id.button)

        button.setText("Pay ${message} Rs.")
        button
            .setOnClickListener{
                startActivity(Intent(this@MainActivity2,MainActivity::class.java))
            }
    }
}
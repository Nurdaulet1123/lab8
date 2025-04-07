package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MusicActivity : AppCompatActivity() {

    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        serviceIntent = Intent(this, MyService::class.java)
    }

    fun startService(view: View) {
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService(view: View) {
        stopService(serviceIntent)
    }
}
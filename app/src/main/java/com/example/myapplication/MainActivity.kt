package com.example.myapplication

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.localbroadcastmanager.content.LocalBroadcastManager
class MainActivity : AppCompatActivity() {

    private lateinit var randomCharacterEditText: EditText
    private lateinit var serviceIntent: Intent
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randomCharacterEditText = findViewById(R.id.editText_randomCharacter)
        serviceIntent = Intent(this, RandomCharacterService::class.java)

        // Инициализация BroadcastReceiver для обработки broadcast сообщений
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val character = intent?.getCharExtra("randomCharacter", '?')
                randomCharacterEditText.setText(character.toString())
            }
        }

        // Регистрация кнопок
        findViewById<Button>(R.id.button_start).setOnClickListener { onClick(it) }
        findViewById<Button>(R.id.button_end).setOnClickListener { onClick(it) }
        findViewById<Button>(R.id.button_next).setOnClickListener { onClick(it) }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.button_start -> {
                // Правильный способ запуска ForegroundService
                ContextCompat.startForegroundService(this, serviceIntent)
            }
            R.id.button_end -> {
                stopService(serviceIntent)
                randomCharacterEditText.setText("")
            }
            R.id.button_next -> {
                startActivity(Intent(this, MusicActivity::class.java))
            }
        }
    }


    override fun onStart() {
        super.onStart()
        // Регистрируем broadcastReceiver через LocalBroadcastManager
        val filter = IntentFilter("my.custom.action.tag.lab6")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        // Отменяем регистрацию broadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

}

package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.util.*

class RandomCharacterService : Service() {

    private var isRandomGeneratorOn = false
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
    private val TAG = "RandomCharacterService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "Service Started", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service started...")

        isRandomGeneratorOn = true

        Thread {
            while (isRandomGeneratorOn) {
                try {
                    Thread.sleep(1000)
                    val randomIdx = Random().nextInt(alphabet.size)
                    val myRandomCharacter = alphabet[randomIdx]
                    Log.i(TAG, "Generated: $myRandomCharacter")

                    val broadcastIntent = Intent("my.custom.action.tag.lab6")
                    broadcastIntent.putExtra("randomCharacter", myRandomCharacter)
                    sendBroadcast(broadcastIntent)
                } catch (e: InterruptedException) {
                    Log.e(TAG, "Thread interrupted")
                }
            }
        }.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRandomGeneratorOn = false
        Toast.makeText(applicationContext, "Service Stopped", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Service Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
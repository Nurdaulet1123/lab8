package com.example.myapplication

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private lateinit var soundPlayer: MediaPlayer
    private val CHANNEL_ID = "channelId"

    override fun onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
        soundPlayer = MediaPlayer.create(this, R.raw.song)
        soundPlayer.isLooping = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Music Channel"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("My Music Player")
            .setContentText("Music is playing")
            .build()

        startForeground(1, notification)
        soundPlayer.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPlayer.stop()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
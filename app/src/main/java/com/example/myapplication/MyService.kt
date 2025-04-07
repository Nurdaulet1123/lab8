package com.example.myapplication

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private lateinit var soundPlayer: MediaPlayer
    private val CHANNEL_ID = "music_channel" // совпадает с id канала в NotificationChannel

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show()
        soundPlayer = MediaPlayer.create(this, R.raw.song)
        soundPlayer.isLooping = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "стартуем сервис")

        createNotificationChannel() // создаём канал перед уведомлением

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Музыка")
            .setContentText("Воспроизведение трека")
            .setSmallIcon(R.drawable.music) // Убедись, что эта иконка есть
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .build()

        Log.d("MyService", "Перед startForeground")
        startForeground(1, notification)
        Log.d("MyService", "После startForeground")

        soundPlayer.start()

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for foreground music service"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPlayer.stop()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

abstract class CustomIntentService(threadName: String) : Service() {
    private val serviceThread: HandlerThread = HandlerThread(threadName)
    protected var isStopped = false

    override fun onDestroy() {
        isStopped = true
        startActivity(Intent(this,
            MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(MainActivity.CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY,
                true))
        super.onDestroy()
    }

    override fun onCreate() {
        super.onCreate()
        isStopped = false
        startActivity(Intent(this,
            MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(MainActivity.CUSTOM_TIMER_SERVICE_IS_CREATED_EXTRA_KEY,
                true))
        serviceThread.start()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Handler(serviceThread.looper).post {
            isStopped = false
            onHandleIntent(intent)
        }
        Handler(serviceThread.looper).post {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    abstract fun onHandleIntent(intent: Intent?)
}
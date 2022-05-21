package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread

abstract class CustomIntentService(threadName: String) : Service() {
    private val serviceThread: HandlerThread = HandlerThread(threadName)

    override fun onCreate() {
        super.onCreate()
        serviceThread.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Handler(serviceThread.looper).post {
            onHandleIntent(intent)
        }
        Handler(serviceThread.looper).post {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    abstract fun onHandleIntent(intent: Intent?)
}
package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val TAG = "CUSTOM_TIMER_SERVICE_THREAD"
private const val SERVICE_THREAD_NAME = "SERVICE_THREAD_NAME"

class CustomTimerService : CustomIntentService(SERVICE_THREAD_NAME) {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate() called")
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d(TAG, "onStart() called with: intent = $intent, startId = $startId")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,
            "onStartCommand() called with: intent = $intent, startId = $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent() called with: intent = $intent")

        val timerIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        var timerTime: String

        for (i in 5 downTo 1) {

            for (j in 10 downTo 1) {
                timerTime = if (j == 10) {
                    "$i.0"
                } else {
                    "${i - 1}.$j"
                }
                timerIntent.putExtra(MainActivity.TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY,
                    timerTime)
                startActivity(timerIntent)
                Thread.sleep(100)
                if (timerTime == "0.1") {
                    timerIntent.putExtra(MainActivity.TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY,
                        "0.0")
                    startActivity(timerIntent)
                }
            }
        }
    }
}
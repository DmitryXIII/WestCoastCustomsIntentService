package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.content.Intent
import android.util.Log
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val TAG = "CUSTOM_TIMER_SERVICE_THREAD"
private const val SERVICE_THREAD_NAME = "SERVICE_THREAD_NAME"

class CustomTimerService : CustomIntentService(SERVICE_THREAD_NAME) {

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
                timerIntent.putExtra(MainActivity.CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY,
                    timerTime)
                startActivity(timerIntent)
                Thread.sleep(100)
                if (timerTime == "0.1") {
                    timerIntent.putExtra(MainActivity.CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY,
                        "0.0")
                    startActivity(timerIntent)
                }
            }
        }

        if (!isStopped) {
            startService(Intent(this, TimerService::class.java))
        }
    }
}
package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val TIMER_SERVICE_THREAD_NAME = "TIMER_SERVICE_THREAD_NAME"
private const val TAG = "TIMER_SERVICE_THREAD"


class TimerService : IntentService(TIMER_SERVICE_THREAD_NAME) {

    private var isStopped = false

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        Log.d(TAG, "onCreate() called")
        isStopped = false
        startActivity(Intent(this,
            MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(MainActivity.TIMER_SERVICE_IS_CREATED_EXTRA_KEY,
            true))
        super.onCreate()
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        Log.d(TAG, "onDestroy() called")
        isStopped = true
        startActivity(Intent(this,
            MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .putExtra(MainActivity.TIMER_SERVICE_IS_STOPPED_EXTRA_KEY,
            true))
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        for (i in 1..5) {
            val timerIntent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIMER_SERVICE_VALUE_EXTRA_KEY, i.toString())
            }
            startActivity(timerIntent)
            if (i != 5) {
                Thread.sleep(1000)
            }
        }

        if (!isStopped) {
            startService(Intent(this, CustomTimerService::class.java))
        }
    }
}
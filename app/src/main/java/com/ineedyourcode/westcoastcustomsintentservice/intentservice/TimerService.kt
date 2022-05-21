package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val TIMER_SERVICE_THREAD_NAME = "TIMER_SERVICE_THREAD_NAME"
private const val TAG = "TIMER_SERVICE_THREAD"


class TimerService : IntentService(TIMER_SERVICE_THREAD_NAME) {

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        Log.d(TAG, "onCreate() called")
        super.onCreate()
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        Log.d(TAG, "onDestroy() called")
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(p0: Intent?) {
        for (i in 1..5) {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIMER_SERVICE_EXTRA_KEY, i.toString())
            }
            startActivity(intent)
            if (i != 5) {
                Thread.sleep(1000)
            }
        }
    }
}
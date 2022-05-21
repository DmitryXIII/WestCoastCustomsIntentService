package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val INTENT_SERVICE_THREAD_NAME = "INTENT_SERVICE_THREAD_NAME"
private const val TAG = "INTENT_SERVICE_THREAD_NAME"


class TimerService : IntentService(INTENT_SERVICE_THREAD_NAME) {

    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        Log.d(TAG, "onCreate() called")
        super.onCreate()
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int) {
        Log.d(TAG, "onStart() called with: intent = $intent, startId = $startId")
        super.onStart(intent, startId)
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        Log.d(TAG, "onDestroy() called")
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(p0: Intent?) {
        for (i in 5 downTo 0) {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIME_FROM_INTENT_SERVICE_EXTRA_KEY, i.toString())
            }
            startActivity(intent)
            Thread.sleep(1000)
        }
    }
}
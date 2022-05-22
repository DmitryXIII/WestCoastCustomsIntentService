package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.app.IntentService
import android.content.Intent
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val SERVICE_THREAD_NAME = "TIMER_SERVICE_THREAD_NAME"
private const val TIMER_DELAY = 1000L
private const val TIMER_DURATION_IN_SECONDS = 5

class TimerService : IntentService(SERVICE_THREAD_NAME) {

    private var isStopped = false

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        isStopped = true
        super.onDestroy()
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        for (i in 0..TIMER_DURATION_IN_SECONDS) {
            val timerIntent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIMER_SERVICE_VALUE_EXTRA_KEY, i.toString())
            }
            startActivity(timerIntent)
            if (i != TIMER_DURATION_IN_SECONDS) {
                Thread.sleep(TIMER_DELAY)
            }
        }

        if (!isStopped) {
            startService(Intent(this, CustomTimerService::class.java))
        } else {
            startActivity(Intent(this,
                MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(MainActivity.TIMER_SERVICE_IS_STOPPED_EXTRA_KEY,
                    true))
        }
    }
}
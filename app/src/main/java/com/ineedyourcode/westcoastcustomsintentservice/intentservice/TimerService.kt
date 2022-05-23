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
        val timerIntent = Intent(this, MainActivity::class.java)

        for (i in TIMER_DURATION_IN_SECONDS downTo 0) {
            startActivity(timerIntent.apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIMER_SERVICE_VALUE_EXTRA_KEY, i.toString())
            })

            if (i != 0) {
                Thread.sleep(TIMER_DELAY)
            }
        }

        if (!isStopped) {
            startService(Intent(this, CustomTimerService::class.java))
        } else {
            startActivity(timerIntent.apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra(MainActivity.TIMER_SERVICE_IS_STOPPED_EXTRA_KEY,
                    true)
            })
        }
    }
}
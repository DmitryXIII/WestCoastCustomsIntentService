package com.ineedyourcode.westcoastcustomsintentservice.intentservice

import android.content.Intent
import com.ineedyourcode.westcoastcustomsintentservice.MainActivity

private const val SERVICE_THREAD_NAME = "CUSTOM_TIMER_SERVICE_THREAD"
private const val TIMER_DURATION_IN_SECONDS = 5
private const val TIMER_DELAY = 100L
private const val LAST_TIMER_VALUE = "0.1"
private const val STOPPED_TIMER_VALUE = "0.0"

class CustomTimerService : CustomIntentService(SERVICE_THREAD_NAME) {

    private val timerIntent = Intent(MainActivity.ACTION_TIMER_TIME)

    override fun onHandleIntent(intent: Intent?) {
        var timerTime: String

        for (i in TIMER_DURATION_IN_SECONDS downTo 1) {
            for (j in 10 downTo 1) {
                timerTime = if (j == 10) {
                    "$i.0"
                } else {
                    "${i - 1}.$j"
                }

                sendBroadcast(timerIntent
                    .putExtra(MainActivity.CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY,
                        timerTime))

                Thread.sleep(TIMER_DELAY)

                if (timerTime == LAST_TIMER_VALUE) {
                    sendBroadcast(timerIntent
                        .putExtra(MainActivity.CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY,
                            STOPPED_TIMER_VALUE))
                }
            }
        }

        if (!isStopped) {
            startService(Intent(this, TimerService::class.java))
        } else {
            sendBroadcast(timerIntent
                .putExtra(MainActivity.CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY,
                    true))
        }
    }
}
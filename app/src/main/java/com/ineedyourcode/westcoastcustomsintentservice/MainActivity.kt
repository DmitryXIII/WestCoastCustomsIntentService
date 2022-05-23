package com.ineedyourcode.westcoastcustomsintentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ineedyourcode.westcoastcustomsintentservice.databinding.ActivityMainBinding
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.CustomTimerService
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.TimerService

class MainActivity : AppCompatActivity() {
    companion object {
        const val ACTION_TIMER_TIME = "ACTION_TIMER_TIME"
        const val TIMER_SERVICE_VALUE_EXTRA_KEY = "TIMER_SERVICE_EXTRA_KEY"
        const val CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY = "CUSTOM_TIMER_SERVICE_EXTRA_KEY"
        const val TIMER_SERVICE_IS_STOPPED_EXTRA_KEY = "TIMER_SERVICE_IS_STOPPED_EXTRA_KEY"
        const val CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY =
            "CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY"
    }

    private lateinit var binding: ActivityMainBinding

    private val timerReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            checkIntent(intent)
        }
    }

    private val timerServiceIntent by lazy {
        Intent(this, TimerService::class.java)
    }

    private val customTimerServiceIntent by lazy {
        Intent(this, CustomTimerService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(timerReceiver, IntentFilter(ACTION_TIMER_TIME))

        binding.startAndroidIntentServiceButton.setOnClickListener {
            startServices()
        }

        binding.stopAllServicesButton.setOnClickListener {
            stopServices()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    private fun stopServices() {
        stopService(timerServiceIntent)
        stopService(customTimerServiceIntent)
    }

    private fun startServices() {
        startService(timerServiceIntent)
        binding.startAndroidIntentServiceButton.isEnabled = false
    }

    private fun checkIntent(intent: Intent?) {
        if (intent?.extras?.containsKey(TIMER_SERVICE_VALUE_EXTRA_KEY) == true) {
            binding.timerForAndroidIntentServiceTextView.text =
                intent.getStringExtra(TIMER_SERVICE_VALUE_EXTRA_KEY)
        }

        if (intent?.extras?.containsKey(TIMER_SERVICE_IS_STOPPED_EXTRA_KEY) == true) {
            binding.startAndroidIntentServiceButton.isEnabled = true
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY) == true) {
            binding.timerForCustomIntentServiceTextView.text =
                intent.getStringExtra(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY)
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY) == true) {
            binding.startAndroidIntentServiceButton.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timerReceiver)
    }
}
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

private const val INTENT_SERVICE_TIMER_VALUE_ARG_KEY =
    "INTENT_SERVICE_TIMER_VALUE_ARG_KEY"
private const val CUSTOM_INTENT_SERVICE_TIMER_VALUE_ARG_KEY =
    "CUSTOM_INTENT_SERVICE_TIMER_VALUE_ARG_KEY"
private const val START_SERVICE_BUTTON_STATE_ARG_KEY =
    "START_SERVICE_BUTTON_STATE_ARG_KEY"
private const val STOP_SERVICE_BUTTON_STATE_ARG_KEY =
    "STOP_SERVICE_BUTTON_STATE_ARG_KEY"

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
        binding.stopAllServicesButton.isEnabled = false
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
            binding.stopAllServicesButton.isEnabled = true
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY) == true) {
            binding.timerForCustomIntentServiceTextView.text =
                intent.getStringExtra(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY)
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY) == true) {
            binding.startAndroidIntentServiceButton.isEnabled = true
            binding.stopAllServicesButton.isEnabled = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(INTENT_SERVICE_TIMER_VALUE_ARG_KEY,
                binding.timerForAndroidIntentServiceTextView.text.toString())
            putString(CUSTOM_INTENT_SERVICE_TIMER_VALUE_ARG_KEY,
                binding.timerForCustomIntentServiceTextView.text.toString())
            putBoolean(START_SERVICE_BUTTON_STATE_ARG_KEY,
                binding.startAndroidIntentServiceButton.isEnabled)
            putBoolean(STOP_SERVICE_BUTTON_STATE_ARG_KEY,
                binding.stopAllServicesButton.isEnabled)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.timerForAndroidIntentServiceTextView.text =
            savedInstanceState.getString(INTENT_SERVICE_TIMER_VALUE_ARG_KEY).toString()
        binding.timerForCustomIntentServiceTextView.text =
            savedInstanceState.getString(CUSTOM_INTENT_SERVICE_TIMER_VALUE_ARG_KEY).toString()
        binding.startAndroidIntentServiceButton.isEnabled =
            savedInstanceState.getBoolean(START_SERVICE_BUTTON_STATE_ARG_KEY)
        binding.stopAllServicesButton.isEnabled =
            savedInstanceState.getBoolean(STOP_SERVICE_BUTTON_STATE_ARG_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timerReceiver)
    }
}
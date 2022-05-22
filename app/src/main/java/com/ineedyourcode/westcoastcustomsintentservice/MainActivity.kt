package com.ineedyourcode.westcoastcustomsintentservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ineedyourcode.westcoastcustomsintentservice.databinding.ActivityMainBinding
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.CustomTimerService
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.TimerService

class MainActivity : AppCompatActivity() {
    companion object {
        const val TIMER_SERVICE_VALUE_EXTRA_KEY = "TIMER_SERVICE_EXTRA_KEY"
        const val CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY = "CUSTOM_TIMER_SERVICE_EXTRA_KEY"
        const val TIMER_SERVICE_IS_STOPPED_EXTRA_KEY = "TIMER_SERVICE_IS_STOPPED_EXTRA_KEY"
        const val TIMER_SERVICE_IS_CREATED_EXTRA_KEY = "TIMER_SERVICE_IS_CREATED_EXTRA_KEY"
        const val CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY = "CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY"
        const val CUSTOM_TIMER_SERVICE_IS_CREATED_EXTRA_KEY = "CUSTOM_TIMER_SERVICE_IS_CREATED_EXTRA_KEY"
    }

    private var isTimerServiceStopped = false
    private var isCustomTimerServiceStopped = false
    private var isTimerServicesCreated = false
    private var isCustomTimerServicesCreated = false

    private val timerServiceIntent by lazy {
        Intent(this, TimerService::class.java)
    }

    private val customTimerServiceIntent by lazy {
        Intent(this, CustomTimerService::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            val timerValue = intent.getStringExtra(TIMER_SERVICE_VALUE_EXTRA_KEY)
            binding.timerForAndroidIntentServiceTextView.text = timerValue
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY) == true) {
            val timerValue = intent.getStringExtra(CUSTOM_TIMER_SERVICE_VALUE_EXTRA_KEY)
            binding.timerForCustomIntentServiceTextView.text = timerValue
        }

        if (intent?.extras?.containsKey(TIMER_SERVICE_IS_STOPPED_EXTRA_KEY) == true) {
            isTimerServiceStopped = true
            isTimerServicesCreated = false
        }

        if (intent?.extras?.containsKey(TIMER_SERVICE_IS_CREATED_EXTRA_KEY) == true) {
            isTimerServiceStopped = false
            isTimerServicesCreated = true
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_IS_STOPPED_EXTRA_KEY) == true) {
            isCustomTimerServiceStopped = true
            isCustomTimerServicesCreated = false
        }

        if (intent?.extras?.containsKey(CUSTOM_TIMER_SERVICE_IS_CREATED_EXTRA_KEY) == true) {
            isCustomTimerServiceStopped = false
            isCustomTimerServicesCreated = true
        }

        if (!isCustomTimerServicesCreated && !isTimerServicesCreated) {
            binding.startAndroidIntentServiceButton.isEnabled = true
        }
        intent?.extras?.clear()
    }
}
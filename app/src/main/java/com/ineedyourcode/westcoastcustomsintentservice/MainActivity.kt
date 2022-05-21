package com.ineedyourcode.westcoastcustomsintentservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ineedyourcode.westcoastcustomsintentservice.databinding.ActivityMainBinding
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.CustomTimerService
import com.ineedyourcode.westcoastcustomsintentservice.intentservice.TimerService

class MainActivity : AppCompatActivity() {
    companion object {
        const val TIME_FROM_INTENT_SERVICE_EXTRA_KEY = "TIME_FROM_INTENT_SERVICE_EXTRA_KEY"
        const val TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY =
            "TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startAndroidIntentServiceButton.setOnClickListener {
            startService(Intent(this, TimerService::class.java))
            binding.startAndroidIntentServiceButton.isEnabled = false
        }

        binding.startCustomIntentServiceButton.setOnClickListener {
            startService(Intent(this, CustomTimerService::class.java))
            binding.startCustomIntentServiceButton.isEnabled = false
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.extras?.containsKey(TIME_FROM_INTENT_SERVICE_EXTRA_KEY) == true) {
            binding.timerForAndroidIntentServiceTextView.text =
                intent.getStringExtra(TIME_FROM_INTENT_SERVICE_EXTRA_KEY)
            if (binding.timerForAndroidIntentServiceTextView.text == "0") {
                binding.startAndroidIntentServiceButton.isEnabled = true
            }
        }

        if (intent?.extras?.containsKey(TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY) == true) {
            binding.timerForCustomIntentServiceTextView.text =
                intent.getStringExtra(TIME_FROM_CUSTOM_INTENT_SERVICE_EXTRA_KEY)
            if (binding.timerForCustomIntentServiceTextView.text == "0.0") {
                binding.startCustomIntentServiceButton.isEnabled = true
            }
        }
    }
}
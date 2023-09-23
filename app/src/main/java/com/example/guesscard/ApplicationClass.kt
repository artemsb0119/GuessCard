package com.example.guesscard

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "d22e9382-380f-4c7f-adc8-7ce4f32dea3e"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}